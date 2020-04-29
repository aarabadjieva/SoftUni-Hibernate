package softuni.product_shop_with_xml.domain.dtos.view;


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsDto implements Serializable {

    @XmlAttribute(name = "count")
    private int count;

    @XmlElement(name = "product")
    private Set<ProductDto> products;

    public SoldProductsDto() {
        this.products = new LinkedHashSet<>();
    }
}
