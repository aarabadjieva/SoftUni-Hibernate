package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.CarSeedRootDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.CarsPartsRootDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.CarsShortInfoRootDto;

public interface CarService {

    void seedCars(CarSeedRootDto carDtos);

    CarsShortInfoRootDto getCarsByMake(String make);

    CarsPartsRootDto getCarsWithParts();
}
