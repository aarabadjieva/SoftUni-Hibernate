package softuni.product_shop_with_xml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.product_shop_with_xml.domain.models.Product;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Set<Product> findAllByPriceGreaterThanAndPriceLessThanAndBuyerIsNullOrderByPriceAsc(BigDecimal lowest, BigDecimal highest);

}
