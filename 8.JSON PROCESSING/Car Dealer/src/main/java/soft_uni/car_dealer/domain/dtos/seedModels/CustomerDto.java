package soft_uni.car_dealer.domain.dtos.seedModels;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.entities.Sale;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CustomerDto {

    @Expose
    @NotNull(message = "Customer name may not be null")
    private String name;

    @Expose
    @NotNull(message = "Customer date of birth may not be null")
    private Date birthDate;

    @Expose
    @NotNull(message = "Please mark if the customer is a young driver")
    private Boolean isYoungDriver;

    private Set<Sale> sales;


    public CustomerDto() {
        this.sales = new HashSet<>();
    }

}
