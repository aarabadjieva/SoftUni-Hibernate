package soft_uni.product_shop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.product_shop.domain.dtos.binding.CategorySeedDto;
import soft_uni.product_shop.domain.dtos.view.CategoryStatisticDto;
import soft_uni.product_shop.domain.models.Category;
import soft_uni.product_shop.repositories.CategoryRepository;
import soft_uni.product_shop.services.CategoryService;
import soft_uni.product_shop.utils.impl.ValidatorUtilImpl;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ValidatorUtilImpl validatorUtil;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(ValidatorUtilImpl validatorUtil, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.validatorUtil = validatorUtil;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos
             ) {
            if (!this.validatorUtil.isValid(categorySeedDto)){
                System.out.println(this.validatorUtil.violations(categorySeedDto));
            }else{
                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
                System.out.printf("Category %s successfully added\n", category.getName());
            }
        }
    }

    @Override
    public Set<CategoryStatisticDto> categoryStatistics() {
        return this.categoryRepository.getCategoriesStatistic();
    }
}
