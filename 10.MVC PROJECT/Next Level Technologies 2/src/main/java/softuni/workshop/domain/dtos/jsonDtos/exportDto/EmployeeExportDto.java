package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeExportDto {

    @Expose
    @SerializedName("First Name")
    private String firstName;

    @Expose
    @SerializedName("Last Name")
    private String lastName;

    @Expose
    @SerializedName("Age")
    private int age;

    @Expose
    @SerializedName("Project")
    private ProjectNameDto project;
}
