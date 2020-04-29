package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.CustomerRootSeedDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.BuyerRootDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.CustomerInfoRootDto;


public interface CustomerService {
    void seedCustomers(CustomerRootSeedDto customerDtos);

    CustomerInfoRootDto getOrderedCustomers();

    BuyerRootDto getBuyers();
}
