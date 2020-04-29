package soft_uni.shampoo_company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import soft_uni.shampoo_company.entities.Ingredient;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);
    List<Ingredient> findByNameIsStartingWith(Character letter);
    List<Ingredient> findAllByNameInOrderByPriceAsc(List<String> ingredientNames);

    @Modifying
    @Query("delete from Ingredient as i where i.name = :ingredientName")
    @Transactional
    void deleteIngredientByGivenName(@Param("ingredientName") String ingredientName);

    @Modifying
    @Query("update Ingredient as i set i.price = i.price*1.1")
    @Transactional
    void updateIngredientsPrice();

    @Modifying
    @Query("update Ingredient as i set i.price = 2.00 " +
            "where i in :ingredientList")
    @Transactional
    void updateIngredientsPriceIfInList(@Param("ingredientList") List<Ingredient> ingredientList);
}
