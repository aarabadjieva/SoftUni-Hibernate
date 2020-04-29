package soft_uni.product_shop.services;

import soft_uni.product_shop.domain.dtos.view.SellerDto;
import soft_uni.product_shop.domain.dtos.binding.UserSeedDto;
import soft_uni.product_shop.domain.dtos.view.UsersAndProductsDto;

import java.util.Set;

public interface UserService {

    void seedUsers(UserSeedDto[] userSeedDtos);

    Set<SellerDto> sellersWithSales();

    UsersAndProductsDto usersAndProducts();
}
