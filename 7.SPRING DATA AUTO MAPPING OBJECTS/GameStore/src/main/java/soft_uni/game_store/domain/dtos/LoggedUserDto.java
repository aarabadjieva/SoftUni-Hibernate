package soft_uni.game_store.domain.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.collection.internal.PersistentSet;
import soft_uni.game_store.domain.enums.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class LoggedUserDto {

    private String fullName;
    private Role role;
    private String email;
    private Set<GameViewDto> games;
    private Set<String> shoppingCart = new HashSet<>();

}
