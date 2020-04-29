package soft_uni.car_dealer.domain.dtos.seedModels;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.entities.Supplier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PartDto {

    @Expose
    @NotNull(message = "Part name may not be null")
    private String name;

    @Expose
    @NotNull(message = "Part price may not be null")
    @Min(value = 0, message = "Part price may not be negative")
    private BigDecimal price;

    @Expose
    @NotNull(message = "Part quantity may not be null")
    @Min(value = 0, message = "Part quantity may not be negative")
    private int quantity;

    @Expose
    @NotNull(message = "Part supplier may not be null")
    private Supplier supplier;
}
