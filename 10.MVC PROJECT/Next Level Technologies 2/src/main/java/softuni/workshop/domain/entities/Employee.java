package softuni.workshop.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "first_name")
    @NotNull(message = "Employee first name may not be null")
    @Length(min = 2, message = "Employee first name may be at least 2 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Employee last name may not be null")
    @Length(min = 2, message = "Employee last name may be at least 2 characters")
    private String lastName;

    @Column(name = "age")
    @NotNull(message = "Employee age may not be null")
    @Min(value = 16, message = "Employee has not enough experience")
    private int age;

    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @NotNull(message = "Employee may have a project")
    private Project project;
}
