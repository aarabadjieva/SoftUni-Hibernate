package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.CarDto;
import soft_uni.car_dealer.domain.dtos.viewModels.CarShortInfoDto;
import soft_uni.car_dealer.domain.dtos.viewModels.CarsPartsDto;

import java.util.List;

public interface CarService {

    void seedCars(CarDto[] carDtos);

    List<CarShortInfoDto> getCarsByMake(String make);

    List<CarsPartsDto> getCarsWithParts();
}
