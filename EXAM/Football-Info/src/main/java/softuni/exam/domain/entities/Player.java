package softuni.exam.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.Position;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 3, max = 15)
    private String lastName;

    @Column
    @Min(value = 1)
    @Max(value = 99)
    private int number;

    @Column
    @DecimalMin(value = "0")
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Position position;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private Picture picture;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;
}
