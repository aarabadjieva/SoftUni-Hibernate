package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PictureSeedDto {

    @Expose
    @NotNull
    @Length(min = 2, max = 19)
    private String name;

    @Expose
    @NotNull
    private String dateAndTime;

    @Expose
    @NotNull
    private int car;
}
