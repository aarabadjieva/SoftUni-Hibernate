package softuni.product_shop_with_xml.services;

import softuni.product_shop_with_xml.domain.dtos.binding.ProductRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.ProductInRangeRootDto;

import java.math.BigDecimal;

public interface ProductService {

    void seedProducts(ProductRootDto productRootDto);

    ProductInRangeRootDto productsInRange(BigDecimal lowest, BigDecimal highest);

}
