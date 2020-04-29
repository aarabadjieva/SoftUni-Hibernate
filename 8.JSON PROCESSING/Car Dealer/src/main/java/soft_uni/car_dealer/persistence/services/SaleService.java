package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.viewModels.SaleInfoDto;

import java.util.List;

public interface SaleService {

    void generateSales();

    List<SaleInfoDto> getSales();
}
