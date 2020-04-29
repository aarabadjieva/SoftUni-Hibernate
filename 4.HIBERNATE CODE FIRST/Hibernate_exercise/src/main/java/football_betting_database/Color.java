package football_betting_database;

import javax.persistence.*;

@Entity
@Table(name = "colours")
public class Color extends BaseEntity{

    @Column(name = "name")
    private String name;

    public Color() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
