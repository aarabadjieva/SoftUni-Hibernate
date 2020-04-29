package hiberspring.domain.dtos.importDtos.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCardDto {

    @Expose
    private String number;
}
