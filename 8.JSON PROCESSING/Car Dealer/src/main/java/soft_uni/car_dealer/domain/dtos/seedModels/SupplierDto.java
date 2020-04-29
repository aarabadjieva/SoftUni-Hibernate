package soft_uni.car_dealer.domain.dtos.seedModels;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SupplierDto {

    @Expose
    @NotNull(message = "Supplier name may not be null")
    private String name;

    @Expose
    @NotNull(message = "Please mark if the supplier is importer")
    private Boolean isImporter;
}
