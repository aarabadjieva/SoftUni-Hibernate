package hiberspring.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int population;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "town")
    private List<Branch> branches;

    public Town() {
        this.branches = new ArrayList<>();
    }
}
