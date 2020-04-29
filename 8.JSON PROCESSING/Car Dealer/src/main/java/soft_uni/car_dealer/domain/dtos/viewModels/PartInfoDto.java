package soft_uni.car_dealer.domain.dtos.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class PartInfoDto implements Serializable {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("Price")
    private BigDecimal price;
}
