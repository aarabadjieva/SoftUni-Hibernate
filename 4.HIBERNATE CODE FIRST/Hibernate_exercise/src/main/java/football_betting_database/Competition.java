package football_betting_database;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity{
    private String name;
    @ManyToOne(targetEntity = CompetitionType.class)
    private CompetitionType type;
    @OneToMany(targetEntity = Game.class, mappedBy = "competition")
    private Set<Game> games;

    public Competition() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
