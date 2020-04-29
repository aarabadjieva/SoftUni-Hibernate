package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.SupplierSeedRootDto;
import soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots.SupplierInfoRootDto;

public interface SupplierService {
    void seedSuppliers(SupplierSeedRootDto supplierDtos);

    SupplierInfoRootDto getLocalSuppliers();
}
