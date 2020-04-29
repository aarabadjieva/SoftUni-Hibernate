package soft_uni.product_shop.domain.dtos.binding;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
public class CategorySeedDto implements Serializable {

    @Expose
    @NotNull(message = "Category name cannot be null")
    @Size(min = 3, max = 15, message = "Category name must be between 3 and 15 characters")
    private String name;
}
