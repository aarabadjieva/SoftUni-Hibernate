package soft_uni.product_shop.domain.dtos.binding;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
public class UserSeedDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    @NotNull(message = "Last name cannot be null")
    @Size(min = 3, message = "Last name must be at least 3 characters")
    private String lastName;

    @Expose
    @Min(value = 0, message = "Age cannot be negative number")
    private int age;
}
