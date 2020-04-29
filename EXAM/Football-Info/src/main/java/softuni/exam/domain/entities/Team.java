package softuni.exam.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team extends BaseEntity{

    @Column
    @Length(min = 3, max = 20)
    private String name;


    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private Picture picture;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
    private Set<Player> playersInTeam;

    public Team() {
        this.playersInTeam = new LinkedHashSet<>();
    }
}
