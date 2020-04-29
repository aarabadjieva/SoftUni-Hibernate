package soft_uni.product_shop.domain.dtos.view;


import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class CategoryStatisticDto implements Serializable {

    @Expose
    private String category;

    @Expose
    private int productsCount;

    @Expose
    private double averagePrice;

    @Expose
    private BigDecimal totalRevenue;

    public CategoryStatisticDto(String category, int productsCount, double averagePrice, BigDecimal totalRevenue) {
        this.category = category;
        this.productsCount = productsCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }
}
