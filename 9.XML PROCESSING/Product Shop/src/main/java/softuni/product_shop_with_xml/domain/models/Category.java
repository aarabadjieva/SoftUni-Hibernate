package softuni.product_shop_with_xml.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Setter
@Getter
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(targetEntity = Product.class, mappedBy = "categories")
    private List<Product> products;
}
