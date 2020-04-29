package softuni.product_shop_with_xml.domain.dtos.binding;

import lombok.Getter;
import lombok.Setter;
import softuni.product_shop_with_xml.domain.models.Category;
import softuni.product_shop_with_xml.domain.models.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDto implements Serializable {

    @XmlElement(name = "name")
    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, message = "Product name must be at least 3 characters")
    private String name;

    @XmlElement(name = "price")
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Product price cannot be negative")
    private BigDecimal price;

    private User buyer;

    @NotNull(message = "Product's seller cannot be null")
    private User seller;

    private Set<Category> categories;

}
