package mostwanted.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictImportDto {

    @Expose
    private String name;

    @Expose
    private String townName;
}
