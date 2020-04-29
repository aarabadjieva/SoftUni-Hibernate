package softuni.exam.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "planes")
public class Plane extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String registerNumber;

    @Column(nullable = false)
    private int capacity;

    @Column
    private String airline;
}
