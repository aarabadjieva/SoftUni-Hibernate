package soft_uni.game_store.domain.entities;

import lombok.Getter;
import lombok.Setter;
import soft_uni.game_store.domain.dtos.GameViewDto;
import soft_uni.game_store.domain.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User extends BaseEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "email", nullable = false, unique = true)
    @Pattern(regexp = "^([A-Za-z0-9]+)([A-Za-z0-9\\.\\-\\_])+([A-Za-z0-9])@([A-Za-z0-9]+)([A-Za-z0-9\\.\\-\\_])+\\.([A-Za-z0-9]+)([A-Za-z0-9]+)",
    message = "Incorrect email.")
    private String email;
    @Column(name = "password", nullable = false)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$",
    message = "Password not strong enough, please choose another password. " +
            "Password must be at least 6 symbols and must contain at least 1 uppercase, " +
            "1 lowercase letter and 1 digit.")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(targetEntity = Game.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_games",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "id")})
    private Set<Game> games;

}
