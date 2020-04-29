package soft_uni.car_dealer.persistence.services;

import soft_uni.car_dealer.domain.dtos.seedModels.PartDto;

public interface PartService {

    void seedParts(PartDto[] partDtos);
}
