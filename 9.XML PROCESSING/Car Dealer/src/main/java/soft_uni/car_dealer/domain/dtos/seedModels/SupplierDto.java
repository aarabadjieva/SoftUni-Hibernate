package soft_uni.car_dealer.domain.dtos.seedModels;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierDto {

    @XmlAttribute(name = "name")
    @NotNull(message = "Supplier name may not be null")
    private String name;

    @XmlAttribute(name = "is-importer")
    @NotNull(message = "Please mark if the supplier is importer")
    private Boolean isImporter;
}
