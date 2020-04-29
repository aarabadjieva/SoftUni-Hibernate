package soft_uni.car_dealer.domain.dtos.viewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.entities.Sale;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class CustomerInfoDto implements Serializable {

    @Expose
    @SerializedName("Id")
    private int id;

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("BirthDate")
    private Date birthDate;

    @Expose
    @SerializedName("IsYoungDriver")
    private Boolean isYoungDriver;

    @Expose
    @SerializedName("Sales")
    private Set<SaleEmptyDto> purchases;

    public CustomerInfoDto() {
        this.purchases = new LinkedHashSet<>();
    }
}
