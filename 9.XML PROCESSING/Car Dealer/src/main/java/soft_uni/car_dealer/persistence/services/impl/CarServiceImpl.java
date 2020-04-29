package soft_uni.car_dealer.persistence.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.car_dealer.config.utils.impl.ValidatorUtilImpl;
import soft_uni.car_dealer.domain.dtos.seedModels.CarDto;
import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.CarSeedRootDto;
import soft_uni.car_dealer.domain.dtos.viewModels.CarShortInfoDto;
import soft_uni.car_dealer.domain.dtos.viewModels.CarsPartsDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.CarsPartsRootDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.CarsShortInfoRootDto;
import soft_uni.car_dealer.domain.entities.Car;
import soft_uni.car_dealer.domain.entities.Part;
import soft_uni.car_dealer.persistence.repositories.CarRepository;
import soft_uni.car_dealer.persistence.repositories.PartRepository;
import soft_uni.car_dealer.persistence.services.CarService;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ValidatorUtilImpl validatorUtil;
    private final ModelMapper modelMapper;
    private final Random random;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ValidatorUtilImpl validatorUtil, ModelMapper modelMapper, Random random) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public void seedCars(CarSeedRootDto carDtos) {
        for (CarDto carDto : carDtos.getCarDtos()) {
            addRandomParts(carDto);
            if (!this.validatorUtil.isValid(carDto)){
                System.out.println(this.validatorUtil.violations(carDto));
            }else {
                Car car = this.modelMapper.map(carDto, Car.class);
                this.carRepository.saveAndFlush(car);
            }
        }
    }

    @Override
    public CarsShortInfoRootDto getCarsByMake(String make) {
        CarsShortInfoRootDto carsShortInfoRootDto = new CarsShortInfoRootDto();
        carsShortInfoRootDto.setCarShortInfoDtoList( this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream()
                .map(c->this.modelMapper.map(c, CarShortInfoDto.class))
                .collect(Collectors.toList()));
        return carsShortInfoRootDto;
    }

    @Override
    public CarsPartsRootDto getCarsWithParts() {
        CarsPartsRootDto carsPartsRootDto = new CarsPartsRootDto();
        carsPartsRootDto.setCarsPartsDtoList(this.carRepository.findAll()
                .stream()
                .map(c-> this.modelMapper.map(c, CarsPartsDto.class))
                .collect(Collectors.toList()));
        return carsPartsRootDto;
    }

    private void addRandomParts(CarDto carDto) {
        int countOfParts = this.random.nextInt(10)+10;
        for (int i = 0; i < countOfParts; i++) {
            carDto.getParts().add(getRandomPart());
        }
    }

    private Part getRandomPart() {
        int partId = this.random.nextInt((int) this.partRepository.count()) +1;
        return this.partRepository.getOne(partId);
    }
}
