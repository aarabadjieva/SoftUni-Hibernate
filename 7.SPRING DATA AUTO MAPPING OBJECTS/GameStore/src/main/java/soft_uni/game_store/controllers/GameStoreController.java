package soft_uni.game_store.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.game_store.domain.dtos.LoggedUserDto;
import soft_uni.game_store.domain.dtos.LoginUserDto;
import soft_uni.game_store.domain.dtos.NewGameDto;
import soft_uni.game_store.domain.dtos.UserRegisterDto;
import soft_uni.game_store.domain.enums.Role;
import soft_uni.game_store.services.GameService;
import soft_uni.game_store.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private Scanner scanner;
    private final UserService userService;
    private final GameService gameService;

    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            String[] cmd = scanner.nextLine().split("\\|");
            switch (cmd[0]) {
                case "RegisterUser":
                    if (!cmd[2].equals(cmd[3])) {
                        System.out.println("Passwords does not match!");
                        break;
                    }
                    UserRegisterDto userRegisterDto = new UserRegisterDto(cmd[1], cmd[2], cmd[3], cmd[4]);
                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    LoginUserDto loginUserDto = new LoginUserDto(cmd[1], cmd[2]);
                    System.out.println(this.userService.loginUser(loginUserDto));
                    break;
                case "Logout":
                case "LogoutUser":
                    System.out.println(this.userService.logoutUser());
                    break;
                case "AddGame":
                    if (this.userService.getLoggedUser() == null) {
                        System.out.println("Please login first");
                    } else if (this.userService.getLoggedUser().getRole() != Role.ADMIN) {
                        System.out.println("Only admin can add games");
                    } else {
                        NewGameDto newGame = new NewGameDto(cmd[1], BigDecimal.valueOf(Double.parseDouble(cmd[2])), Double.parseDouble(cmd[3]),
                                cmd[4], cmd[5], cmd[6], LocalDate.parse(cmd[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        System.out.println(this.gameService.addGame(newGame));
                    }
                    break;
                case "EditGame":
                    if (this.userService.getLoggedUser() == null) {
                        System.out.println("Please login first");
                    } else if (this.userService.getLoggedUser().getRole() != Role.ADMIN) {
                        System.out.println("Only admin can edit games");
                    } else {
                        int gameId = Integer.parseInt(cmd[1]);
                        List<String> propertiesToEdit = new ArrayList<>();
                        for (int i = 2; i < cmd.length; i++) {
                            propertiesToEdit.add(cmd[i]);
                        }
                        System.out.println(this.gameService.editGame(gameId, propertiesToEdit));
                    }
                    break;
                case "DeleteGame":
                    if (this.userService.getLoggedUser() == null) {
                        System.out.println("Please login first");
                    } else if (this.userService.getLoggedUser().getRole() != Role.ADMIN) {
                        System.out.println("Only admin can edit games");
                    } else {
                        int gameId = Integer.parseInt(cmd[1]);
                        System.out.println(this.gameService.deleteGame(gameId));
                    }
                    break;
                case "AllGames":
                    System.out.println(this.gameService.allGames());
                    break;
                case "DetailGame":
                    String gameTitle = cmd[1];
                    System.out.println(this.gameService.gameDetails(gameTitle));
                    break;
                case "OwnedGames":
                    if (this.userService.getLoggedUser() == null) {
                        System.out.println("Please login first");
                    } else {
                        System.out.println(this.userService.getOwnedGames());
                    }
                    break;
                case "AddItem":
                    gameTitle = cmd[1];
                    if (this.userService.getLoggedUser() == null) {
                        System.out.println("Please login first");
                    } else {
                        System.out.println(this.userService.addGameInCart(gameTitle));
                    }
                    break;
                case "RemoveItem":
                    gameTitle = cmd[1];
                    if (this.userService.getLoggedUser() == null) {
                        System.out.println("Please login first");
                    } else {
                        System.out.println(this.userService.removeGameFromCart(gameTitle));
                    }
                    break;
                case "BuyItem":
                    System.out.println(this.userService.buyGames());
                    break;
            }
        }
    }

}
