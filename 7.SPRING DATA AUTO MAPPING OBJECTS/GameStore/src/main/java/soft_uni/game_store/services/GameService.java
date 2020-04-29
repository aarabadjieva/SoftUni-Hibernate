package soft_uni.game_store.services;

import soft_uni.game_store.domain.dtos.GameDetailedViewDto;
import soft_uni.game_store.domain.dtos.GameViewDto;
import soft_uni.game_store.domain.dtos.NewGameDto;

import java.util.List;
import java.util.Set;

public interface GameService {

    String addGame(NewGameDto newGame);

    String editGame(int gameId, List<String> properties);

    String deleteGame(int gameId);

    String allGames();

    String gameDetails(String gameTitle);

}
