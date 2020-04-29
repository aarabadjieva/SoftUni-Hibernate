package soft_uni.car_dealer.domain.dtos.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class SaleInfoDto implements Serializable {

    @Expose
    private CarShortInfoDto car;

    @Expose
    private String customerName;

    @Expose
    @SerializedName("Discount")
    private Double discount;

    @Expose
    private BigDecimal price;

    @Expose
    private BigDecimal priceWithDiscount;

}
