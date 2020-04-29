package soft_uni.product_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft_uni.product_shop.domain.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User as u " +
            "join Product as p " +
            "on p.seller.id = u.id " +
            "where p.buyer is not null " +
            "group by u.id " +
            "order by u.lastName, u.firstName")
    List<User> findAllWithSales();

}
