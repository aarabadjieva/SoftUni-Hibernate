package soft_uni.car_dealer.domain.dtos.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CarShortInfoDto implements Serializable {

//   @Expose
//   @SerializedName("Id")
    private int id;

    @Expose
    @SerializedName("Make")
    private String make;

    @Expose
    @SerializedName("Model")
    private String model;

    @Expose
    @SerializedName("TravelledDistance")
    private Long travelledDistance;
}
