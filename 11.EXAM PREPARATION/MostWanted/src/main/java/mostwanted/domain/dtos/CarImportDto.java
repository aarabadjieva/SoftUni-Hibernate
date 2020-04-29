package mostwanted.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarImportDto {

    @Expose
    private String brand;

    @Expose
    private String model;

    @Expose
    private BigDecimal price;

    @Expose
    private int yearOfProduction;

    @Expose
    private double maxSpeed;

    @Expose
    private double zeroToSixty;

    @Expose
    private String racerName;
}
