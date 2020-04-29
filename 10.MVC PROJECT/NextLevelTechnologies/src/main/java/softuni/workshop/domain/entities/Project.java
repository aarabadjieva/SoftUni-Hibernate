package softuni.workshop.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BaseEntity{

    @Column(name = "name")
    @NotNull(message = "Project name may not be null")
    private String name;

    @Column(name = "description")
    @NotNull(message = "Project may have a description")
    private String description;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Column(name = "payment")
    @NotNull(message = "Project payment may not be null")
    private BigDecimal payment;

    @Column(name = "start_date")
    @NotNull(message = "Project may have a start date")
    private String startDate;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @NotNull(message = "Project may be connected to some company")
    private Company company;

    @OneToMany(mappedBy = "project")
    private Set<Employee> employees;
}
