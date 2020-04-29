package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.SaleInfoRootDto;

public interface SaleService {

    void generateSales();

    SaleInfoRootDto getSales();
}
