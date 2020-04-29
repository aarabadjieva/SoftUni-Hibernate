package soft_uni.shampoo_company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.shampoo_company.entities.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

}
