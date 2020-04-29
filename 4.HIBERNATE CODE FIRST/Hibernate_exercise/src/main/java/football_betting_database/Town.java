package football_betting_database;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(name = "name")
    private String name;
    @ManyToOne(targetEntity = Country.class)
    private Country country;
    @OneToMany(targetEntity = Team.class, mappedBy = "town")
    private Set<Team> teams;
    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
