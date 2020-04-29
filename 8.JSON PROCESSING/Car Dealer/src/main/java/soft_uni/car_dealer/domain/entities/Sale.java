package soft_uni.car_dealer.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "sales")
public class Sale extends BaseEntity{

    @OneToOne(optional = false)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @NotNull(message = "Car for sale may not be null")
    private Car car;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @NotNull(message = "Sale customer may not be null")
    private Customer customer;

    @Column(name = "discount")
    @NotNull(message = "Sale discount may not be null")
    @Digits(integer = 1, fraction = 2)
    private Double discount;

}
