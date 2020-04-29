package softuni.product_shop_with_xml.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.product_shop_with_xml.domain.dtos.binding.UserRootDto;
import softuni.product_shop_with_xml.domain.dtos.binding.UserSeedDto;
import softuni.product_shop_with_xml.domain.dtos.view.ProductDto;
import softuni.product_shop_with_xml.domain.dtos.view.SellerDto;
import softuni.product_shop_with_xml.domain.dtos.view.UsersAndProductsDto;
import softuni.product_shop_with_xml.domain.dtos.view.xmlRoots.SellersRootDto;
import softuni.product_shop_with_xml.domain.models.User;
import softuni.product_shop_with_xml.repositories.UserRepository;
import softuni.product_shop_with_xml.services.UserService;
import softuni.product_shop_with_xml.utils.impl.ValidatorUtilImpl;

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
    public void seedUsers(UserRootDto userRootDto) {
        for (UserSeedDto userSeedDto : userRootDto.getUserSeedDtos()
        ) {
            if (!validatorUtil.isValid(userSeedDto)) {
                System.out.println(validatorUtil.violations(userSeedDto));
            } else {
                User user = this.modelMapper.map(userSeedDto, User.class);
                this.userRepository.saveAndFlush(user);
            }
        }
    }

    @Override
    public SellersRootDto sellersWithSales() {
        SellersRootDto sellersRootDto = new SellersRootDto();
        Set<SellerDto> sellerDtos = this.userRepository
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
        sellersRootDto.setSellerDtos(sellerDtos);
        return sellersRootDto;
    }

    @Override
    public UsersAndProductsDto usersAndProducts() {
        List<SellerDto> sellerDtos = new ArrayList<>(this.sellersWithSales().getSellerDtos()).stream().sorted((s1, s2)->{
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
