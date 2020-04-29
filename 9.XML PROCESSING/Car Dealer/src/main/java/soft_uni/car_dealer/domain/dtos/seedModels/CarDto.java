package soft_uni.car_dealer.domain.dtos.seedModels;

import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.entities.Part;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDto {

    @XmlElement(name = "make")
    @NotNull(message = "Car make may not be null")
    private String make;

    @XmlElement(name = "model")
    @NotNull(message = "Car model may not be null")
    private String model;

    @XmlElement(name = "travelled-distance")
    @Min(value = 0, message = "Car travelled distance may not be negative")
    private Long travelledDistance;

    @Size(min = 10, max = 20, message = "Car may contain between 10 and 20 parts")
    private List<Part> parts;


    public CarDto() {
        this.parts = new ArrayList<>();
    }
}
