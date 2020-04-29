package soft_uni.product_shop.services;

import soft_uni.product_shop.domain.dtos.view.ProductInRangeDto;
import soft_uni.product_shop.domain.dtos.binding.ProductSeedDto;

import java.math.BigDecimal;
import java.util.Set;

public interface ProductService {

    void seedProducts(ProductSeedDto[] productSeedDtos);

    Set<ProductInRangeDto> productsInRange(BigDecimal lowest, BigDecimal highest);

}
