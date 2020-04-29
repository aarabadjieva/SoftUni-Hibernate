package mostwanted.repository;

import mostwanted.domain.dtos.exportDtos.TownExportDto;
import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town, Integer> {
    Optional<Town> findByName(String name);

    @Query("select new mostwanted.domain.dtos.exportDtos.TownExportDto(t.name, count(r.id)) " +
            "from Town t join Racer r " +
            "on t.id = r.homeTown.id " +
            "group by t.id " +
            "order by count(r.id) desc , t.name")
    List<TownExportDto> findAllByCountOfRacers();
}
