package mostwanted.domain.dtos.races;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceImportDto {

    @XmlElement(name = "laps")
    private int laps;

    @XmlElement(name = "district-name")
    private String districtName;

    @XmlElement(name = "entries")
    private EntryImportRootDto entryImportRootDto;
}
