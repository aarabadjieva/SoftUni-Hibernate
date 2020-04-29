package softuni.exam.models.dtos.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TownSeedDto {

    @Expose
    @NotNull
    @Length(min = 2)
    private String name;

    @Expose
    @NotNull
    @Min(1)
    private int population;

    @Expose
    @NotNull
    private String guide;
}
