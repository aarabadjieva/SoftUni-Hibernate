package softuni.exam.models.dtos.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PassengerSeedDto {

    @Expose
    @NotNull
    @Length(min = 2)
    private String firstName;

    @Expose
    @NotNull
    @Length(min = 2)
    private String lastName;

    @Expose
    @NotNull
    @Min(1)
    private int age;

    @Expose
    @NotNull
    private String phoneNumber;

    @Expose
    @NotNull
    @Pattern(regexp = ".*@.*\\..*")
    private String email;

    @Expose
    @SerializedName("town")
    @NotNull
    @Length(min = 2)
    private String townName;
}
