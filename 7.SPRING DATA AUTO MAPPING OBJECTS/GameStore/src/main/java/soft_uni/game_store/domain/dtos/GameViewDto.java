package soft_uni.game_store.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class GameViewDto {

    private String title;
    private BigDecimal price;
}
