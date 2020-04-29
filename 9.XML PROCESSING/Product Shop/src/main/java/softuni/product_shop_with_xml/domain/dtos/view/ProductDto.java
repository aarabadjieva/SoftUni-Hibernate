package softuni.product_shop_with_xml.domain.dtos.view;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductDto implements Serializable {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "price")
    private BigDecimal price;

    @XmlTransient
//    @XmlElement(name = "buyer-first-name")
    private String buyerFirstName;

    @XmlTransient
//    @XmlElement(name = "buyer-last-name")
    private String buyerLastName;
}
