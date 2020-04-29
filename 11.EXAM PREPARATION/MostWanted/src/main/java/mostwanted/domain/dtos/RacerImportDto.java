package mostwanted.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RacerImportDto {

    @Expose
    private String name;

    @Expose
    private int age;

    @Expose
    private BigDecimal bounty;

    @Expose
    private String homeTown;
}
