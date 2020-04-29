package soft_uni.car_dealer.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.car_dealer.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
