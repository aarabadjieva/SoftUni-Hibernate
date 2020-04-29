package softuni.exam.domain.dtos.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import softuni.exam.domain.dtos.xml.PictureDto;
import softuni.exam.domain.dtos.xml.TeamDto;

import java.math.BigDecimal;

@Getter
@Setter
public class PlayerDto {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int number;

    @Expose
    private BigDecimal salary;

    @Expose
    @SerializedName("position")
    private String positionName;

    @Expose
    @SerializedName("picture")
    private PictureDto pictureDto;

    @Expose
    @SerializedName("team")
    private TeamDto teamDto;
}
