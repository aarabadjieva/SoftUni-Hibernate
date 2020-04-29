package softuni.exam.models.dtos.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "from-town")
@XmlAccessorType(XmlAccessType.FIELD)
public class FromTownXmlDto {

    @XmlElement
    private String name;
}
