package soft_uni.product_shop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.product_shop.domain.dtos.view.ProductInRangeDto;
import soft_uni.product_shop.domain.dtos.binding.ProductSeedDto;
import soft_uni.product_shop.domain.models.Category;
import soft_uni.product_shop.domain.models.Product;
import soft_uni.product_shop.domain.models.User;
import soft_uni.product_shop.repositories.CategoryRepository;
import soft_uni.product_shop.repositories.ProductRepository;
import soft_uni.product_shop.repositories.UserRepository;
import soft_uni.product_shop.services.ProductService;
import soft_uni.product_shop.utils.impl.ValidatorUtilImpl;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ValidatorUtilImpl validatorUtil;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ValidatorUtilImpl validatorUtil, ModelMapper modelMapper, ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            productSeedDto.setSeller(this.getRandomUser());
            User user = this.getRandomUser();
            if (user.getId() % 4 == 0) {
                productSeedDto.setBuyer(null);
            } else {
                productSeedDto.setBuyer(user);
            }
            productSeedDto.setCategories(getRandomCategories());
            if (!this.validatorUtil.isValid(productSeedDto)) {
                System.out.println(this.validatorUtil.violations(productSeedDto));
            } else {
                Product product = this.modelMapper.map(productSeedDto, Product.class);
                this.productRepository.saveAndFlush(product);
                System.out.printf("Product %s successfully added\n", product.getName());
            }
        }
    }

    @Override
    public Set<ProductInRangeDto> productsInRange(BigDecimal lowest, BigDecimal highest) {
        Set<Product> products = this.productRepository.findAllByPriceGreaterThanAndPriceLessThanAndBuyerIsNullOrderByPriceAsc(lowest, highest);
        Set<ProductInRangeDto> productsInRange = new LinkedHashSet<>();
        for (Product product : products) {
            ProductInRangeDto productInRange = this.modelMapper.map(product, ProductInRangeDto.class);
            productInRange.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));
            productsInRange.add(productInRange);
        }
        return productsInRange;
    }

    private User getRandomUser() {
        Random random = new Random();
        int id = random.nextInt((int) this.userRepository.count() - 1) + 1;
        return this.userRepository.getOne(id);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();
        Random random = new Random();
        int length = random.nextInt((int) this.categoryRepository.count());
        for (int i = 0; i < length; i++) {
            int categoryId = random.nextInt((int) this.categoryRepository.count() - 1) + 1;
            Category category = this.categoryRepository.findById(categoryId).orElse(null);
            categories.add(category);
        }
        return categories;
    }
 }
