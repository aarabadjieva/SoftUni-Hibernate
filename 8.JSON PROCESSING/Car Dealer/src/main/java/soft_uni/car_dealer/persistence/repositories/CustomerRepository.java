package soft_uni.car_dealer.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_uni.car_dealer.domain.entities.Customer;

import java.util.List;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c " +
            "order by c.birthDate ASC, c.isYoungDriver")
    Set<Customer> findAllByBirthDate();

    @Query("select c from Customer c " +
            "where c.sales.size > :size")
    List<Customer> getCustomersWithCarsBought(@Param("size") int size );

}
