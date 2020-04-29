package alararestaurant.domain.dtos.importDto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemRootDto {

    @XmlElement(name = "item")
    private List<ItemOrderDto> items;
}
