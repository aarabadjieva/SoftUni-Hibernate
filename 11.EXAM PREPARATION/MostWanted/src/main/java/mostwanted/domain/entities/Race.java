package mostwanted.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "races")
public class Race extends BaseEntity{

    @Column(name = "laps")
    @NotNull
    private int laps;

    @ManyToOne(optional = false)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    @OneToMany(mappedBy = "race")
    private List<RaceEntry> entries;

    public Race() {
        this.laps = 0;
        this.entries = new ArrayList<>();
    }
}
