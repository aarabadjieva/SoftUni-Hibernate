package softuni.exam.models.dtos.xml;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.util.LocalDateTimeAdapter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {

    @XmlElement(name = "serial-number")
    @NotNull
    @Length(min = 2)
    private String serialNumber;

    @XmlElement
    @NotNull
    @DecimalMin("0")
    private BigDecimal price;

    @XmlElement(name = "take-off")
    @NotNull
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime takeoff;

    @XmlElement(name = "from-town")
    @NotNull
    private FromTownXmlDto fromTown;

    @XmlElement(name = "to-town")
    @NotNull
    private ToTownXmlDto toTown;

    @XmlElement
    @NotNull
    private PassengerXmlDto passenger;

    @XmlElement
    @NotNull
    private PlaneSeedDto plane;
}
