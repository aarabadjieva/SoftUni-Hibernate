package soft_uni.car_dealer.domain.dtos.seedModels;

import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.entities.Supplier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Getter
@Setter
@XmlRootElement(name = "part")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartDto {

    @XmlAttribute(name = "name")
    @NotNull(message = "Part name may not be null")
    private String name;

    @XmlAttribute(name = "price")
    @NotNull(message = "Part price may not be null")
    @Min(value = 0, message = "Part price may not be negative")
    private BigDecimal price;

    @XmlAttribute(name = "quantity")
    @NotNull(message = "Part quantity may not be null")
    @Min(value = 0, message = "Part quantity may not be negative")
    private int quantity;

    @NotNull(message = "Part supplier may not be null")
    private Supplier supplier;
}
