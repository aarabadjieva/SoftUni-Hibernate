package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.CarSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static softuni.exam.common.Constants.*;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper, ValidationUtil validator, Gson gson) {
        this.carRepository = carRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public Car findCarById(int id) {
        return this.carRepository.findById(id).orElse(null);
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count()>0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_JSON_FILES + "cars.json"));
    }

    @Override
    public String importCars() throws IOException {
        CarSeedDto[] carSeedDtos = this.gson.fromJson(readCarsFileContent(), CarSeedDto[].class);
        List<String> messages = new ArrayList<>();
        for (CarSeedDto carDto : carSeedDtos) {
            if (validator.isValid(carDto)){
                if (this.carRepository.findByMakeAndModelAndKilometers(carDto.getMake(),
                        carDto.getModel(), carDto.getKilometers())==null){
                    Car car = this.mapper.map(carDto, Car.class);
                    LocalDate registeredOn = LocalDate.parse(carDto.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    car.setRegisteredOn(registeredOn);
                    this.carRepository.saveAndFlush(car);
                    messages.add(String.format(SUCCESSFULLY_IMPORTED_MESSAGE, car.getClass().getSimpleName().toLowerCase(),
                            car.getMake(), car.getModel()));
                }
            }else {
                messages.add(String.format(INVALID_DATA_MESSAGE, "car"));
            }
        }
        return String.join("\n", messages);
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        List<Car> carsByPicturesCount = this.carRepository.findCarsByPicturesCountOrderedByPicCountAndMake();
        List<String> result = new ArrayList<>();
        carsByPicturesCount.stream().forEach(c->
                result.add(String.format("Car make - %s, model - %s\n" +
                        "\tKilometers - %d\n" +
                        "\tRegistered on - %s\n" +
                        "\tNumber of pictures - %d\n", c.getMake(),
                        c.getModel(), c.getKilometers(), c.getRegisteredOn(), c.getPictures().size())));
        return String.join("\n", result);
    }
}
