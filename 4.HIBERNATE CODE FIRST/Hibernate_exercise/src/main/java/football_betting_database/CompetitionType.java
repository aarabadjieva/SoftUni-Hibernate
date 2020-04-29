package football_betting_database;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "competition_types")
public class CompetitionType extends BaseEntity{

    private String name;
    @OneToMany(targetEntity = Competition.class, mappedBy = "type")
    private Set<Competition> competitions;

    public CompetitionType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }

}
