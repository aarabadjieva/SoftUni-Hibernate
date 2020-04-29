package soft_uni.product_shop.domain.dtos.view;


import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UsersAndProductsDto implements Serializable {

    @Expose
    private int usersCount;

    @Expose
    private List<SellerDto> users;
}
