package softuni.product_shop_with_xml.services;

import softuni.product_shop_with_xml.domain.dtos.binding.CategoryRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.CategoryStatisticRootDto;

public interface CategoryService {

    void seedCategories(CategoryRootDto categoryRootDto);

    CategoryStatisticRootDto categoryStatistics();
}
