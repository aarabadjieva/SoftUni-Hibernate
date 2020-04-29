package soft_uni.product_shop.domain.dtos.view;


import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SoldProductsDto implements Serializable {

    @Expose
    private int count;

    @Expose
    private Set<ProductDto> products;

    public SoldProductsDto() {
        this.products = new HashSet<>();
    }
}
