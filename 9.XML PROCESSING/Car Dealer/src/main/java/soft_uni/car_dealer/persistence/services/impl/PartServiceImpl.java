package soft_uni.car_dealer.persistence.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.car_dealer.config.utils.impl.ValidatorUtilImpl;
import soft_uni.car_dealer.domain.dtos.seedModels.PartDto;
import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.PartsSeedRootDto;
import soft_uni.car_dealer.domain.entities.Part;
import soft_uni.car_dealer.domain.entities.Supplier;
import soft_uni.car_dealer.persistence.repositories.PartRepository;
import soft_uni.car_dealer.persistence.repositories.SupplierRepository;
import soft_uni.car_dealer.persistence.services.PartService;

import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtilImpl validatorUtil;
    private final SupplierRepository supplierRepository;
    private final Random random;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidatorUtilImpl validatorUtil, SupplierRepository supplierRepository, Random random) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.supplierRepository = supplierRepository;
        this.random = random;
    }

    @Override
    public void seedParts(PartsSeedRootDto partDtos) {
        for (PartDto partDto : partDtos.getPartDtos()) {
            partDto.setSupplier(getRandomSupplier());
            if (!this.validatorUtil.isValid(partDto)) {
                System.out.println(this.validatorUtil.violations(partDto));
            } else {
                Part part = this.modelMapper.map(partDto, Part.class);
                this.partRepository.saveAndFlush(part);
            }
        }
    }

    private Supplier getRandomSupplier() {
        int supplierId = this.random.nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.getOne(supplierId);
    }
}
