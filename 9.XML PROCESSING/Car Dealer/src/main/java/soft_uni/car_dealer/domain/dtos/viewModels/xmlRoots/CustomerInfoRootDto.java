package soft_uni.car_dealer.domain.dtos.viewModels.xmlRoots;

import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.dtos.viewModels.CustomerInfoDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInfoRootDto {

    @XmlElement(name = "customer")
    private Set<CustomerInfoDto> customerInfoDtos;
}
