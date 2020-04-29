package soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots;

import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.dtos.seedModels.SupplierDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierDto> supplierDtos;
}
