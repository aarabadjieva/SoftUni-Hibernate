package softuni.exam.models.dtos.xml;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedDto {

    @XmlElement(name = "register-number")
    @NotNull
    @Length(min = 5)
    private String registerNumber;

    @XmlElement
    @NotNull
    @Min(1)
    private int capacity;

    @XmlElement
    @NotNull
    @Length(min = 2)
    private String airline;
}
