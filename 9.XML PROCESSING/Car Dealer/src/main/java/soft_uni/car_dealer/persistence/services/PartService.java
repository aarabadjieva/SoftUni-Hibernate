package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots.PartsSeedRootDto;

public interface PartService {

    void seedParts(PartsSeedRootDto partDtos);
}
