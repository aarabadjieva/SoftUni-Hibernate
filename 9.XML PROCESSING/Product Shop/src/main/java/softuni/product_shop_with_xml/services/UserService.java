package softuni.product_shop_with_xml.services;

import softuni.product_shop_with_xml.domain.dtos.binding.UserRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.SellersRootDto;
import softuni.product_shop_with_xml.domain.dtos.view.UsersAndProductsDto;

public interface UserService {

    void seedUsers(UserRootDto userRootDto);

    SellersRootDto sellersWithSales();

    UsersAndProductsDto usersAndProducts();
}
