package alararestaurant.domain.dtos.importDto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionImportDto {

    @Expose
    private String name;

    public PositionImportDto(String name) {
        this.name = name;
    }
}
