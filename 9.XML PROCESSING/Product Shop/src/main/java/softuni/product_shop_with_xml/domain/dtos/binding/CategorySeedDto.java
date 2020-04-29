package softuni.product_shop_with_xml.domain.dtos.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Setter
@Getter
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySeedDto implements Serializable {

    @XmlElement(name = "name")
    @NotNull(message = "Category name cannot be null")
    @Size(min = 3, max = 15, message = "Category name must be between 3 and 15 characters")
    private String name;
}
