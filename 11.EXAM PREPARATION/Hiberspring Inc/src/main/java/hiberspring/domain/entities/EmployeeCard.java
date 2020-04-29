package hiberspring.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employee_cards")
public class EmployeeCard extends BaseEntity{

    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "card")
    private Employee employee;
}
