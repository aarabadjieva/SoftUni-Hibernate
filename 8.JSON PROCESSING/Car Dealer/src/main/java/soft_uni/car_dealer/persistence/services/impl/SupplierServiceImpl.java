package soft_uni.car_dealer.persistence.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.car_dealer.config.utils.impl.ValidatorUtilImpl;
import soft_uni.car_dealer.domain.dtos.seedModels.SupplierDto;
import soft_uni.car_dealer.domain.dtos.viewModels.SupplierInfoDto;
import soft_uni.car_dealer.domain.entities.Supplier;
import soft_uni.car_dealer.persistence.repositories.SupplierRepository;
import soft_uni.car_dealer.persistence.services.SupplierService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtilImpl validatorUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidatorUtilImpl validatorUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedSuppliers(SupplierDto[] supplierDtos) {
        for (SupplierDto supplierDto : supplierDtos) {
            if (!this.validatorUtil.isValid(supplierDto)){
                System.out.println(this.validatorUtil.violations(supplierDto));
            }else {
                Supplier supplier = this.modelMapper.map(supplierDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);
            }
        }
    }

    @Override
    public List<SupplierInfoDto> getLocalSuppliers() {
        return this.supplierRepository.getAllByIsImporterIsFalse()
                .stream()
                .map(s->{
                    SupplierInfoDto supplier = this.modelMapper.map(s, SupplierInfoDto.class);
                    supplier.setPartsCount(s.getParts().size());
                    return supplier;
                })
                .collect(Collectors.toList());
    }
}
