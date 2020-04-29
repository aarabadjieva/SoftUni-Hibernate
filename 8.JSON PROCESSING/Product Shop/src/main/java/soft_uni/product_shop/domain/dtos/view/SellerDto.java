package soft_uni.product_shop.domain.dtos.view;


import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SellerDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;


    private Set<ProductDto> soldProducts;

    @Expose
    private SoldProductsDto soldProduct;

    public SellerDto() {
        this.soldProducts = new HashSet<>();
        this.soldProduct = new SoldProductsDto();
    }
}
