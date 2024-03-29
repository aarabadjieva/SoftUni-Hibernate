package entities.shampoos;

import entities.ingredients.BasicIngredient;
import entities.labels.BasicLabel;


import java.math.BigDecimal;
import java.util.Set;

public interface Shampoo {
    long getId();
    void setId(long id);
    String getBrand();
    void setBrand(String brand);
    BigDecimal getPrice();
    void setPrice(BigDecimal price);
    Size getSize();
    void setSize(Size size);
    BasicLabel getLabel();
    void setLabel(BasicLabel Label);
    Set<BasicIngredient> getIngredients();
    void setIngredients(Set<BasicIngredient> ingredients);
}
