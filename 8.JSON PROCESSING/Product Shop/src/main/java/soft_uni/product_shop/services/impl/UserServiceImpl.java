package soft_uni.product_shop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.product_shop.domain.dtos.view.SellerDto;
import soft_uni.product_shop.domain.dtos.view.ProductDto;
import soft_uni.product_shop.domain.dtos.binding.UserSeedDto;
import soft_uni.product_shop.domain.dtos.view.UsersAndProductsDto;
import soft_uni.product_shop.domain.models.User;
import soft_uni.product_shop.repositories.UserRepository;
import soft_uni.product_shop.services.UserService;
import soft_uni.product_shop.utils.impl.ValidatorUtilImpl;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ValidatorUtilImpl validatorUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(ValidatorUtilImpl validatorUtil, UserRepository userRepository, ModelMapper modelMapper) {
        this.validatorUtil = validatorUtil;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos
        ) {
            if (!validatorUtil.isValid(userSeedDto)) {
                System.out.println(validatorUtil.violations(userSeedDto));
            } else {
                User user = this.modelMapper.map(userSeedDto, User.class);
                this.userRepository.saveAndFlush(user);
                System.out.printf("User %s successfully added\n", user.getLastName());
            }
        }
    }

    @Override
    public Set<SellerDto> sellersWithSales() {
        return this.userRepository
                .findAllWithSales().stream()
                .map(u -> {
                    final SellerDto sellerDto =
                            this.modelMapper.map(u, SellerDto.class);
                    sellerDto.setSoldProducts(u.getSoldProducts()
                            .stream()
                            .filter(p -> p.getBuyer() != null)
                            .map(p -> this.modelMapper.map(p, ProductDto.class))
                            .collect(Collectors.toSet()));
                    sellerDto.getSoldProduct().setProducts(sellerDto.getSoldProducts());
                    sellerDto.getSoldProduct().setCount(sellerDto.getSoldProducts().size());
                    return sellerDto;
                })
                .collect(Collectors.toSet());
    }

    @Override
    public UsersAndProductsDto usersAndProducts() {
        List<SellerDto> sellerDtos = new ArrayList<>(this.sellersWithSales()).stream().sorted((s1,s2)->{
            int cmp = s2.getSoldProducts().size() - s1.getSoldProducts().size();
            if(cmp==0){
                cmp = s1.getLastName().compareTo(s2.getLastName());
            }
            return cmp;
        }).collect(Collectors.toList());
        UsersAndProductsDto usersAndProductsDto = new UsersAndProductsDto();
        usersAndProductsDto.setUsers(sellerDtos);
        usersAndProductsDto.setUsersCount(sellerDtos.size());
        return usersAndProductsDto;
    }


}
