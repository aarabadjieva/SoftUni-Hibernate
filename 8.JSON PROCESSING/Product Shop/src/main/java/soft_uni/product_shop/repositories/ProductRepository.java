package soft_uni.product_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.product_shop.domain.models.Product;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Set<Product> findAllByPriceGreaterThanAndPriceLessThanAndBuyerIsNullOrderByPriceAsc(BigDecimal lowest, BigDecimal highest);

}
