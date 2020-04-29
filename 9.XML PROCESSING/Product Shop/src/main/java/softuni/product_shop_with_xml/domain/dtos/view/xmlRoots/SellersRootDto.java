package softuni.product_shop_with_xml.domain.dtos.view.xmlRoots;

import lombok.Getter;
import lombok.Setter;
import softuni.product_shop_with_xml.domain.dtos.view.SellerDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Getter
@Setter
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersRootDto {

    @XmlElement(name = "user")
    private Set<SellerDto> sellerDtos;
}
