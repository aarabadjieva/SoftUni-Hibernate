package soft_uni.product_shop.domain.dtos.binding;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import soft_uni.product_shop.domain.models.Category;
import soft_uni.product_shop.domain.models.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
public class ProductSeedDto implements Serializable {

    @Expose
    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, message = "Product name must be at least 3 characters")
    private String name;

    @Expose
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Product price cannot be negative")
    private BigDecimal price;

    @Expose
    private User buyer;

    @Expose
    @NotNull(message = "Product's seller cannot be null")
    private User seller;

    @Expose
    private Set<Category> categories;

}
