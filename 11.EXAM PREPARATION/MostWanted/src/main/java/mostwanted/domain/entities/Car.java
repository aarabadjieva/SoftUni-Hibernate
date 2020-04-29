package mostwanted.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    @Column(name = "brand")
    @NotNull
    private String brand;

    @Column(name = "model")
    @NotNull
    private String model;

    @Column(name = "price")
    @PositiveOrZero
    private BigDecimal price;

    @Column(name = "year_of_production")
    private int yearOfProduction;

    @Column(name = "max_speed")
    @PositiveOrZero
    private double maxSpeed;

    @Column(name = "zero_to_sixty")
    @PositiveOrZero
    private double zeroToSixty;

    @ManyToOne
    @JoinColumn(name = "racer_id", referencedColumnName = "id")
    private Racer racer;

    @Override
    public String toString() {
        return String.format("%s %s %d\n", this.getBrand(), this.getModel(), this.getYearOfProduction());

    }
}
