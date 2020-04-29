package soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots;

import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.dtos.viewModels.SupplierInfoDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierInfoRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierInfoDto> supplierInfoDtos;
}
