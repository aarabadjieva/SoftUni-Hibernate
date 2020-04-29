package softuni.exam.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "passengers")
public class Passenger extends BaseEntity{

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String  lastName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Town town;

    @OneToMany(mappedBy = "passenger", fetch = FetchType.EAGER)
    private List<Ticket> tickets;
}
