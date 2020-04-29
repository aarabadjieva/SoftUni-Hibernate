package entities.ingredients;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "AM")
public class AmmoniumChloride extends BasicChemicalIngredient {
    public AmmoniumChloride() {
        super("Ammonium Chloride", new BigDecimal(0.59), "Formula NH4Cl");
    }
}
