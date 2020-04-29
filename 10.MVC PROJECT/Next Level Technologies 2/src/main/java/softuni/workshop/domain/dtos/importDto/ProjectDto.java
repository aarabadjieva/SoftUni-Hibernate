package softuni.workshop.domain.dtos.importDto;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDto {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "is-finished")
    private Boolean isFinished;

    @XmlElement(name = "payment")
    private BigDecimal payment;

    @XmlElement(name = "start-date")
    private String startDate;

    @XmlElement(name = "company")
    private CompanyDto company;
}
