package alararestaurant.domain.dtos.importDto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryImportDto {

    @Expose
    private String name;

    public CategoryImportDto(String name) {
        this.name = name;
    }
}
