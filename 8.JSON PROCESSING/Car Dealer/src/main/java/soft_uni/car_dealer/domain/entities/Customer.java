package soft_uni.car_dealer.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "young_driver")
    private Boolean isYoungDriver;

    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;
}
