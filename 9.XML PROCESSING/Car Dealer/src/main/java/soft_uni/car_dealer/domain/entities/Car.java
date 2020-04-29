package soft_uni.car_dealer.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cars")
public class Car extends BaseEntity{

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "travelled_distance")
    private Long travelledDistance;

    @ManyToMany
    @JoinTable(name = "parts_cars",
    joinColumns = {@JoinColumn(name = "car_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "part_id", referencedColumnName = "id")})
    private List<Part> parts;
}
