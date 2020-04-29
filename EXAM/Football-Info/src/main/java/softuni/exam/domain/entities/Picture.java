package softuni.exam.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column()
    @NotNull
    private String url;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "picture")
    private Set<Team> teams;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "picture")
    private Set<Player> players;

    public Picture() {
        this.teams = new LinkedHashSet<>();
        this.players = new LinkedHashSet<>();
    }
}
