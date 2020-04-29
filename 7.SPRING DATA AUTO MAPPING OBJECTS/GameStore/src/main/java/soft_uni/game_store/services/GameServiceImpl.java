package soft_uni.game_store.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.game_store.domain.dtos.GameDetailedViewDto;
import soft_uni.game_store.domain.dtos.GameViewDto;
import soft_uni.game_store.domain.dtos.NewGameDto;
import soft_uni.game_store.domain.entities.Game;
import soft_uni.game_store.repositories.GameRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public String addGame(NewGameDto newGame) {
        Game game = this.modelMapper.map(newGame, Game.class);
        StringBuilder sb = new StringBuilder();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Game>> violations = validator.validate(game);
        if (violations.size() != 0) {
            for (ConstraintViolation<Game> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString().trim();
        }
        try {
            this.gameRepository.saveAndFlush(game);
        }catch (Exception e){
            return "This game is already added. You can try the edit options";
        }

        return String.format("Added %s",game.getTitle());
    }

    @Override
    public String editGame(int gameId, List<String> properties) {
        Game game = this.gameRepository.getGameById(gameId).orElse(null);
        if (game == null){
            return "Game with such an ID does not exist in here. Try to add it";
        }
        for (String property:properties
             ) {
            String[] values = property.split("=");
            switch (values[0]){
                case "title":
                    game.setTitle(values[1]);
                    break;
                case "trailer":
                    game.setTrailer(values[1]);
                    break;
                case "image thumbnail":
                    game.setImageThumbnail(values[1]);
                    break;
                case "size":
                    game.setSize(Double.parseDouble(values[1]));
                    break;
                case "price":
                    game.setPrice(BigDecimal.valueOf(Double.parseDouble(values[1])));
                    break;
                case "description":
                    game.setDescription(values[1]);
                    break;
                case "release date":
                    game.setReleaseDate(LocalDate.parse(values[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    break;
            }
        }
        StringBuilder sb = new StringBuilder();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Game>> violations = validator.validate(game);
        if (violations.size() != 0) {
            for (ConstraintViolation<Game> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString().trim();
        }
        this.gameRepository.saveAndFlush(game);
        return String.format("Edited %s",game.getTitle());
    }

    @Override
    public String deleteGame(int gameId) {
        Game game = this.gameRepository.findById(gameId).orElse(null);
        if (game==null){
            return "Game with such an ID does not exist in here";
        }
        this.gameRepository.deleteById(gameId);
        return String.format("Deleted %s", game.getTitle());
    }

    @Override
    public String allGames() {
        List<GameViewDto> games = this.gameRepository.findAll().stream()
                .map(g->this.modelMapper.map(g,GameViewDto.class))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        games.forEach(g-> sb.append(String.format("%s %.2f\n", g.getTitle(), g.getPrice())));
        return sb.toString().trim();
    }

    @Override
    public String gameDetails(String gameTitle) {
        Game game = this.gameRepository.getGameByTitle(gameTitle).orElse(null);
        if (game==null){
            return "Sorry, we do not have this game. See if the name is written correct";
        }
        GameDetailedViewDto detailedGame = this.modelMapper.map(game, GameDetailedViewDto.class);
        return String.format("Title: %s\n" +
                "Price: %.2f \n" +
                "Description: %s \n" +
                "Release date: %s\n", detailedGame.getTitle(), detailedGame.getPrice(),
                detailedGame.getDescription(), detailedGame.getReleaseDate());
    }
}
