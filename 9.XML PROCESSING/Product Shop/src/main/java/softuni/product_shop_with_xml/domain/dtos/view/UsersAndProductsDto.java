package softuni.product_shop_with_xml.domain.dtos.view;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductsDto implements Serializable {

    @XmlAttribute(name = "count")
    private int usersCount;

    @XmlElement(name = "user")
    private List<SellerDto> users;
}
