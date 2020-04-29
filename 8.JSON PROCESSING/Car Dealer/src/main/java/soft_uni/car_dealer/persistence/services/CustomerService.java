package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.CustomerDto;
import soft_uni.car_dealer.domain.dtos.viewModels.BuyerDto;
import soft_uni.car_dealer.domain.dtos.viewModels.CustomerInfoDto;

import java.util.List;
import java.util.Set;


public interface CustomerService {
    void seedCustomers(CustomerDto[] customerDtos);

    Set<CustomerInfoDto> getOrderedCustomers();

    List<BuyerDto> getBuyers();
}
