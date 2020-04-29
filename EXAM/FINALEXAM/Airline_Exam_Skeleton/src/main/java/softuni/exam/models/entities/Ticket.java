package softuni.exam.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime takeoff;

    @ManyToOne
    private Town fromTown;

    @ManyToOne
    private Town toTown;

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Plane plane;
}
