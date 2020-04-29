package hiberspring.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String position;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "card_id", referencedColumnName = "id", unique = true)
    private EmployeeCard card;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Branch branch;
}
