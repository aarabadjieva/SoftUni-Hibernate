package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.SupplierDto;
import soft_uni.car_dealer.domain.dtos.viewModels.SupplierInfoDto;

import java.util.List;

public interface SupplierService {
    void seedSuppliers(SupplierDto[] supplierDtos);

    List<SupplierInfoDto> getLocalSuppliers();
}
