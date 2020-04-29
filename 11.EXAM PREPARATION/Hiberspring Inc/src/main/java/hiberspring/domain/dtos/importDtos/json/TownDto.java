package hiberspring.domain.dtos.importDtos.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TownDto {

    @Expose
    @NotNull
    private String name;

    @Expose
    @Min(value = 1)
    private int population;
}
