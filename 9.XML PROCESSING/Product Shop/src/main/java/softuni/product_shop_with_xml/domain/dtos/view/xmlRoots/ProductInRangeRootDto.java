package softuni.product_shop_with_xml.domain.dtos.view.xmlRoots;

import lombok.Getter;
import lombok.Setter;
import softuni.product_shop_with_xml.domain.dtos.view.ProductInRangeDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "productsInRange")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInRangeRootDto {

    @XmlElement(name = "product")
    private Set<ProductInRangeDto> productsInRange;
}
