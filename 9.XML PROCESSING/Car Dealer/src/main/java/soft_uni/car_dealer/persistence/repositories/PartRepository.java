package soft_uni.car_dealer.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.car_dealer.domain.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
}
