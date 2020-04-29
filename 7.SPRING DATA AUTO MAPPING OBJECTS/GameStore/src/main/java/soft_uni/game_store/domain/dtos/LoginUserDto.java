package soft_uni.game_store.domain.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUserDto {

    private String email;
    private String password;

    public LoginUserDto() {
    }

    public LoginUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
