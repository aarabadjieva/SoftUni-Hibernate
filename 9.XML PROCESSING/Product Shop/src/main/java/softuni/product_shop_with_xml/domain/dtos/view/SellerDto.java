package softuni.product_shop_with_xml.domain.dtos.view;


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerDto implements Serializable {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute(name = "age")
    private int age;

    @XmlTransient
//    @XmlElementWrapper(name = "sold-products")
//    @XmlElement(name = "product")
    private Set<ProductDto> soldProducts;

    @XmlElement(name = "sold-products")
    private SoldProductsDto soldProduct;

    public SellerDto() {
        this.soldProducts = new LinkedHashSet<>();
        this.soldProduct = new SoldProductsDto();
    }
}
