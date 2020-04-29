package softuni.product_shop_with_xml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.product_shop_with_xml.domain.dtos.view.CategoryStatisticDto;
import softuni.product_shop_with_xml.domain.models.Category;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select new softuni.product_shop_with_xml.domain.dtos.view.CategoryStatisticDto( " +
            "c.name, c.products.size, avg(p.price), sum(p.price)) " +
            "from Category as c " +
            "join c.products as p " +
            "group by c.id " +
            "order by c.products.size desc")
    Set<CategoryStatisticDto> getCategoriesStatistic();

}
