package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{

    @Column
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "gold_status")
    private boolean hasGoldStatus;

    @Column(name = "addedd_on")
    private LocalDateTime addedOn;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Car car;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Seller seller;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Picture> pictures;

}
