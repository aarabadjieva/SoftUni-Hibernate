package soft_uni.game_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.game_store.domain.entities.Game;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> getGameById(int id);

    void deleteById(int id);

    List<Game> findAll();

    Optional<Game> getGameByTitle(String gameTitle);

}
