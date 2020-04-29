package softuni.product_shop_with_xml.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.product_shop_with_xml.domain.dtos.binding.CategoryRootDto;
import softuni.product_shop_with_xml.domain.dtos.binding.CategorySeedDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.CategoryStatisticRootDto;
import softuni.product_shop_with_xml.domain.models.Category;
import softuni.product_shop_with_xml.repositories.CategoryRepository;
import softuni.product_shop_with_xml.services.CategoryService;
import softuni.product_shop_with_xml.utils.impl.ValidatorUtilImpl;

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
    public void seedCategories(CategoryRootDto categoryRootDto) {
        for (CategorySeedDto categorySeedDto : categoryRootDto.getCategorySeedDtos()
             ) {
            if (!this.validatorUtil.isValid(categorySeedDto)){
                System.out.println(this.validatorUtil.violations(categorySeedDto));
            }else{
                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public CategoryStatisticRootDto categoryStatistics() {
        CategoryStatisticRootDto categoryStatisticRootDto = new CategoryStatisticRootDto();
        categoryStatisticRootDto.setCategoryStatisticDtos(this.categoryRepository.getCategoriesStatistic());
        return categoryStatisticRootDto;
    }
}
