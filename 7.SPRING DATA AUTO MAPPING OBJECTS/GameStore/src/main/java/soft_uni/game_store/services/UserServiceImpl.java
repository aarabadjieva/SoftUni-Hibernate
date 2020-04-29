package soft_uni.game_store.services;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.game_store.domain.dtos.GameViewDto;
import soft_uni.game_store.domain.dtos.LoggedUserDto;
import soft_uni.game_store.domain.dtos.LoginUserDto;
import soft_uni.game_store.domain.dtos.UserRegisterDto;
import soft_uni.game_store.domain.entities.Game;
import soft_uni.game_store.domain.entities.User;
import soft_uni.game_store.domain.enums.Role;
import soft_uni.game_store.repositories.GameRepository;
import soft_uni.game_store.repositories.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private ModelMapper modelMapper;
    private LoggedUserDto loggedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.modelMapper = new ModelMapper();
        this.loggedUser = null;
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {

        User user = this.modelMapper.map(userRegisterDto, User.class);
        StringBuilder sb = new StringBuilder();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.size() != 0) {
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString().trim();
        }

        if (this.userRepository.count() == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        try {
            this.userRepository.saveAndFlush(user);
        } catch (Exception e) {
            return "This email is already registered";
        }

        return String.format("%s was registered", user.getFullName());
    }

    @Override
    public String loginUser(LoginUserDto loginUserDto) {
        if (this.loggedUser != null) {
            return "Please, logout first";
        }
        User user = this.userRepository.findUserByEmailAndPassword(loginUserDto.getEmail(), loginUserDto.getPassword()).orElse(null);
        if (user == null) {
            return "Incorrect username / password";
        }

        Converter<Set<Game>, Set<GameViewDto>> userToLoggedUser = new AbstractConverter<Set<Game>, Set<GameViewDto>>() {

            @Override
            protected Set<GameViewDto> convert(Set<Game> games) {
                return games.stream()
                        .map(g -> modelMapper.map(g, GameViewDto.class))
                        .collect(Collectors.toSet());
            }
        };

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.modelMapper.addConverter(userToLoggedUser);
        LoggedUserDto loggedUserDto = this.modelMapper.map(user, LoggedUserDto.class);


        this.loggedUser = loggedUserDto;
        return String.format("Successfully logged in %s", loggedUserDto.getFullName());
    }

    @Override
    public String logoutUser() {
        if (this.loggedUser == null) {
            return "Cannot log out. No user was logged in.";
        }
        String userName = this.loggedUser.getFullName();
        this.loggedUser = null;
        return String.format("User %s successfully logged out", userName);
    }

    @Override
    public LoggedUserDto getLoggedUser() {
        return this.loggedUser;
    }

    @Override
    public String getOwnedGames() {
        if (this.loggedUser.getGames().size() == 0) {
            return "You do not own any games yet";
        }
        StringBuilder sb = new StringBuilder();
        this.loggedUser.getGames().forEach(g -> sb.append(g.getTitle()).append(System.lineSeparator()));
        return sb.toString().trim();
    }

    @Override
    public String addGameInCart(String gameTitle) {
        Game game = this.gameRepository.getGameByTitle(gameTitle).orElse(null);
        if (game == null) {
            return "Sorry, we do not have this game.";
        }
        for (GameViewDto ownedGame : this.loggedUser.getGames()
        ) {
            if (ownedGame.getTitle().equals(game.getTitle())) {
                return "You already own this game.";
            }
        }
        if (this.loggedUser.getShoppingCart().contains(gameTitle)) {
            return "You have already added this game.";
        }
        this.loggedUser.getShoppingCart().add(gameTitle);
        return String.format("%s added to cart.", gameTitle);
    }

    @Override
    public String removeGameFromCart(String gameTitle) {
        Game game = this.gameRepository.getGameByTitle(gameTitle).orElse(null);
        if (!this.loggedUser.getShoppingCart().contains(gameTitle)) {
            return "You have not added this game.";
        }
        this.loggedUser.getShoppingCart().remove(gameTitle);
        return String.format("%s removed from cart.", gameTitle);
    }

    @Override
    public String buyGames() {
        if (this.loggedUser.getShoppingCart().size() == 0) {
            return "You have not chosen any games.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Successfully bought games:\n");
        User user = this.userRepository.findUserByEmail(this.loggedUser.getEmail());
        for (String gameTitle : this.loggedUser.getShoppingCart()
        ) {
            Game game = this.gameRepository.getGameByTitle(gameTitle).orElse(null);
            this.loggedUser.getGames().add(this.modelMapper.map(game, GameViewDto.class));
            user.getGames().add(game);
            sb.append(String.format(" -%s", gameTitle)).append(System.lineSeparator());
            this.loggedUser.getShoppingCart().remove(gameTitle);
        }
        this.userRepository.saveAndFlush(user);
        return sb.toString();
    }
}
