package softuni.workshop.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company extends BaseEntity{

    @Column(name = "name")
    @NotNull(message = "Company name may not be null")
    @Length(min = 2, message = "Company name may be at least 2 characters")
    private String name;

    @OneToMany(mappedBy = "company")
    private Set<Project> projects;
}
