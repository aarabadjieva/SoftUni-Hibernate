package football_betting_database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "continents")
public class Continent extends BaseEntity{

    @Column(name = "name")
    private String name;
    @OneToMany(targetEntity = Country.class, mappedBy = "continent")
    private Set<Country> countries;

    public Continent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
