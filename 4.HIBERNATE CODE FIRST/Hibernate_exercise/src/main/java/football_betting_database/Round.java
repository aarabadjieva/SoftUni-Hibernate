package football_betting_database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "rounds")
public class Round extends BaseEntity{
    @Column(name = "name")
    private String name;
    @OneToMany(targetEntity = Game.class, mappedBy = "round")
    private Set<Game> games;

    public Round() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
