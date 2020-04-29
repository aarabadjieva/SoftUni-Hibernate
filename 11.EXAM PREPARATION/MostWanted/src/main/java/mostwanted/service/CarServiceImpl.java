package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/files/cars.json";

    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validator, Gson gson) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public Boolean carsAreImported() {
        return carRepository.count()>0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) throws IOException {
        carsFileContent = readCarsJsonFile();
        CarImportDto[] cars = gson.fromJson(carsFileContent, CarImportDto[].class);
        List<String> result = new ArrayList<>();
        for (CarImportDto carDto : cars) {
            Racer racer = racerRepository.findByName(carDto.getRacerName()).orElse(null);
            Car car = modelMapper.map(carDto, Car.class);
            if (racer==null||!validator.isValid(car)){
                result.add("Error: Incorrect data!");
                continue;
            }
            car.setRacer(racer);
            try {
                carRepository.saveAndFlush(car);
                result.add(String.format("Successfully imported Car – %s %s @ %d.",
                        car.getBrand(), car.getModel(), car.getYearOfProduction()));
            }catch (Exception e){
                result.add("Error: Duplicate Data!");
            }
        }
        return String.join("\n", result);
    }
}
