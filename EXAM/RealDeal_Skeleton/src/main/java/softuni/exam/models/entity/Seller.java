package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.Rating;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity{

    @Column(name = "first_name", unique = true)
    private String firstName;

    @Column(name = "last_name", unique = true)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column
    private String town;
}
