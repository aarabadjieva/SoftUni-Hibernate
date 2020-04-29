package mostwanted.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "race_entries")
public class RaceEntry extends BaseEntity{

    @Column(name = "has_finished")
    private Boolean hasFinished;

    @Column(name = "finish_time")
    private double finishTime;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "racer_id", referencedColumnName = "id")
    private Racer racer;
}
