package soft_uni.car_dealer.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.car_dealer.domain.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String model);

}
