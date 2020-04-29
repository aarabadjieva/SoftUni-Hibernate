package hiberspring.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "branches")
public class Branch extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @OneToMany(mappedBy = "branch")
    private List<Product> products;

    @OneToMany(mappedBy = "branch")
    private List<Employee> employees;

    public Branch() {
        this.products = new ArrayList<>();
        this.employees = new ArrayList<>();
    }
}
