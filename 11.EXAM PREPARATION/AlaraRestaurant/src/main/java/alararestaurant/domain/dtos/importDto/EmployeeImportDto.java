package alararestaurant.domain.dtos.importDto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeImportDto {

    @Expose
    private String name;

    @Expose
    private int age;

    @Expose
    private String position;
}
