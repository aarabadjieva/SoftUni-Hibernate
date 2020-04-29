package soft_uni.product_shop.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private Set<Product> soldProducts;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private Set<Product> boughtProducts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_friends",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "friend_id")})
    private Set<User> friends;
}
