package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class CarSeedDto {

    @Expose
    @NotNull
    @Length(min = 2, max = 20)
    private String make;

    @Expose
    @NotNull
    @Length(min = 2, max = 20)
    private String model;

    @Expose
    @PositiveOrZero
    private int kilometers;

    @Expose
    @NotNull
    private String registeredOn;

}
