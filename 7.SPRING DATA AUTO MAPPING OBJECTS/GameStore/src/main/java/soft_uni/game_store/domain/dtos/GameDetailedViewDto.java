package soft_uni.game_store.domain.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class GameDetailedViewDto {

    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

}
