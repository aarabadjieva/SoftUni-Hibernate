package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyExportDto {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("Projects")
    private List<ProjectNameDto> projectNameDtoList;
}
