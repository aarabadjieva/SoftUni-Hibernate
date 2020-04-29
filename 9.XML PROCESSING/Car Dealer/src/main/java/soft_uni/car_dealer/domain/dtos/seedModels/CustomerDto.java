package soft_uni.car_dealer.domain.dtos.seedModels;

import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.entities.Sale;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerDto {

    @XmlAttribute(name = "name")
    @NotNull(message = "Customer name may not be null")
    private String name;

    @XmlElement(name = "birth-date")
    @NotNull(message = "Customer date of birth may not be null")
    private Date birthDate;

    @XmlElement(name = "is-young-driver")
    @NotNull(message = "Please mark if the customer is a young driver")
    private Boolean isYoungDriver;

    private Set<Sale> sales;


    public CustomerDto() {
        this.sales = new HashSet<>();
    }

}
