package soft_uni.product_shop.domain.dtos.view;


import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;


    private String buyerFirstName;


    private String buyerLastName;
}
