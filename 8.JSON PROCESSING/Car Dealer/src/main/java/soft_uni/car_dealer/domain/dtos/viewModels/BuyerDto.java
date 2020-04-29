package soft_uni.car_dealer.domain.dtos.viewModels;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class BuyerDto implements Serializable {

    @Expose
    private String fullName;

    @Expose
    private int boughtCars;

    @Expose
    @Digits(integer = 9, fraction = 2)
    private BigDecimal spendMoney;

}
