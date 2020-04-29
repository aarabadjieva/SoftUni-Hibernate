package soft_uni.car_dealer.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "suppliers")
public class Supplier extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "is_importer")
    private Boolean isImporter;

    @OneToMany(mappedBy = "supplier")
    private Set<Part> parts;
}
