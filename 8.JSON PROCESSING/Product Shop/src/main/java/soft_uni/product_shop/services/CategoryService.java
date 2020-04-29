package soft_uni.product_shop.services;

import soft_uni.product_shop.domain.dtos.binding.CategorySeedDto;
import soft_uni.product_shop.domain.dtos.view.CategoryStatisticDto;

import java.util.Set;

public interface CategoryService {

    void seedCategories(CategorySeedDto[] categorySeedDtos);

    Set<CategoryStatisticDto> categoryStatistics();
}
