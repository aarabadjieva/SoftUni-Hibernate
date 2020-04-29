package softuni.product_shop_with_xml.domain.dtos.view.xmlRoots;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import softuni.product_shop_with_xml.domain.dtos.view.CategoryStatisticDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class CategoryStatisticRootDto implements Serializable {

    @XmlElement(name = "category")
    private Set<CategoryStatisticDto> categoryStatisticDtos;
}
