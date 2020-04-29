package mostwanted.domain.dtos.raceentries;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "race-entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportDto {

    @XmlAttribute(name = "has-finished")
    private Boolean hasFinished;

    @XmlAttribute(name = "finish-time")
    private double finishTime;

    @XmlAttribute(name = "car-id")
    private int carId;

    @XmlElement(name = "racer")
    private String racerName;
}
