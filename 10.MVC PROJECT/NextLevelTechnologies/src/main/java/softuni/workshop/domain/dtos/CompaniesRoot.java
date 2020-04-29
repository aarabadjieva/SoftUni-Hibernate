package softuni.workshop.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompaniesRoot {

    @XmlElement(name = "company")
    private List<CompanyDto> companyDtoList;

    public List<CompanyDto> getCompanyDtoList() {
        return companyDtoList;
    }
}
