package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Offer;

import java.time.LocalDateTime;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    Offer findByDescriptionAndAddedOn(String description, LocalDateTime addedOn);
    
}
