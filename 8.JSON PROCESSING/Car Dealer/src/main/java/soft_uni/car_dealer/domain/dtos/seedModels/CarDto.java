package soft_uni.car_dealer.domain.dtos.seedModels;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import soft_uni.car_dealer.domain.entities.Part;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CarDto {

    @Expose
    @NotNull(message = "Car make may not be null")
    private String make;

    @Expose
    @NotNull(message = "Car model may not be null")
    private String model;

    @Expose
    @Min(value = 0, message = "Car travelled distance may not be negative")
    private Long travelledDistance;

    @Expose
    @Size(min = 10, max = 20, message = "Car may contain between 10 and 20 parts")
    private List<Part> parts;


    public CarDto() {
        this.parts = new ArrayList<>();
    }
}
