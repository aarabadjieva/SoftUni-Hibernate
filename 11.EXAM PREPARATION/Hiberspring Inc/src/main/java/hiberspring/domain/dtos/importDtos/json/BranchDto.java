package hiberspring.domain.dtos.importDtos.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BranchDto {

    @NotNull
    @Expose
    private String name;

    @NotNull
    @Expose
    private String town;
}
