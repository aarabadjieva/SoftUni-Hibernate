package softuni.workshop.domain.dtos.jsonDtos.exportDto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProjectExportDto {

    @Expose
    @SerializedName("Project Name")
    private String name;

    @Expose
    @SerializedName("Description")
    private String description;

    @Expose
    @SerializedName("Is Finished")
    private Boolean isFinished;

    @Expose
    @SerializedName("Payment")
    private BigDecimal payment;

    @Expose
    @SerializedName("Start Date")
    private String startDate;

    @Expose
    @SerializedName("Company")
    private CompanyNameDto company;
}
