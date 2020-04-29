package soft_uni.game_store.services;

import soft_uni.game_store.domain.dtos.LoggedUserDto;
import soft_uni.game_store.domain.dtos.LoginUserDto;
import soft_uni.game_store.domain.dtos.UserRegisterDto;

public interface UserService {

    String registerUser(UserRegisterDto userRegisterDto);

    String loginUser(LoginUserDto loginUserDto);

    String logoutUser();

    LoggedUserDto getLoggedUser();

    String getOwnedGames();


    String addGameInCart(String gameTitle);

    String removeGameFromCart(String gameTitle);

    String buyGames();
}
