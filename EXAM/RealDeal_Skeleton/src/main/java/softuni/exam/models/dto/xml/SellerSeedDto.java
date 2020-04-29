package softuni.exam.models.dto.xml;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entity.enums.Rating;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedDto {

    @XmlElement(name = "first-name")
    @NotNull
    @Length(min = 2, max = 19)
    private String firstName;

    @XmlElement(name = "last-name")
    @NotNull
    @Length(min = 2, max = 19)
    private String lastName;

    @XmlElement
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @XmlElement
    @NotNull
    private Rating rating;

    @XmlElement
    @NotNull
    private String town;
}
