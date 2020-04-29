package soft_uni.car_dealer.domain.dtos.seedModels.xmlSeedRoots;

import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.dtos.seedModels.CarDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDto {

    @XmlElement(name = "car")
    private List<CarDto> carDtos;
}
