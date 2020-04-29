package soft_uni.car_dealer.domain.dtos.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SupplierInfoDto implements Serializable {

    @Expose
    @SerializedName("Id")
    private int id;

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    private int partsCount;
}
