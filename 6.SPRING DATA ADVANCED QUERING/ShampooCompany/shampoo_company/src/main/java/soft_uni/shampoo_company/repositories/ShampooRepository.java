package soft_uni.shampoo_company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_uni.shampoo_company.entities.Ingredient;
import soft_uni.shampoo_company.entities.Shampoo;
import soft_uni.shampoo_company.entities.Size;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository <Shampoo, Long>{
    List<Shampoo> findBySizeOrderById(Size size);
    List<Shampoo> findBySizeOrLabel_IdOrderByPriceAsc(Size size, Long labelId);
    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);
    List<Shampoo> findByPriceLessThan(BigDecimal price);

    @Query("select s from Shampoo as s inner join s.ingredients as i " +
            "where i in :ingredients")
    List<Shampoo> findAllByIngredients(List<Ingredient> ingredients);

    @Query("select s from Shampoo as s " +
            "where s.ingredients.size < :ingrCount")
    List<Shampoo> findAllByIngredientsCount(int ingrCount);

    @Query("select sum(i.price) from Shampoo as s join s.ingredients as i " +
            "where s.brand = :brand")
    BigDecimal getIngredientsCostForShampoo(@Param("brand") String brandName);


}
