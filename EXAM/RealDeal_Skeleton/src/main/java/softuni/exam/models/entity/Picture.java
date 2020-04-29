package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(unique = true)
    private String name;

    @Column(name = "date_time")
    private LocalDateTime dateAndTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Car car;

}
