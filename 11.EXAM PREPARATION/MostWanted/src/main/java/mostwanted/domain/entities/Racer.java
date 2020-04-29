package mostwanted.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "racers")
public class Racer extends BaseEntity{

    @Column(name = "name", unique = true)
    @NotNull
    private String name;

    @Column(name = "age")
    @PositiveOrZero
    private Integer age;

    @Column(name = "bounty")
    @PositiveOrZero
    private BigDecimal bounty;

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town homeTown;

    @OneToMany(mappedBy = "racer")
    private List<Car> cars;

    @Override
    public String toString() {
        if (this.getAge() == null||this.getAge()==0){
            return String.format("Name: %s\nCars:\n%s",
                    this.getName(), this.getCars().toString())
                    .replaceAll("[\\[,\\],\\,]", "");
        }
        return String.format("Name: %s\nAge: %d\nCars:\n%s",
                this.getName(), this.getAge(), this.getCars().toString()
        ).replaceAll("[\\[,\\],\\,]", "");
    }

}
