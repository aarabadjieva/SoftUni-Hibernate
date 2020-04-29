package football_betting_database;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(nullable = false, unique = true, length = 3)
    private String id;
    @Column(name = "name")
    private String name;
    @ManyToOne(targetEntity = Continent.class)
    private Continent continent;
    @OneToMany(targetEntity = Town.class, mappedBy = "country")
    private Set<Town> towns;

    public Country() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public Set<Town> getTowns() {
        return towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }
}
