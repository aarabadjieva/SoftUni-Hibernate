package mostwanted.domain.dtos.exportDtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TownExportDto {

    private String name;
    private Long racersCount;

    @Override
    public String toString() {
        return String.format("Name: %s\nRacers:%s\n", this.getName(), this.getRacersCount());
    }
}
