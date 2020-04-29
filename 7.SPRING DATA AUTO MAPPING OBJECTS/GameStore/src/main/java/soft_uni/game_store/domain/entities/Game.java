package soft_uni.game_store.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "games")
@Setter
@Getter
public class Game extends BaseEntity{

    @Column(name = "title", nullable = false, unique = true)
    @Pattern(regexp = "^([A-Z].+)", message = "Title is not correct")
    @Length(min = 3, message = "Title too short")
    @Length(max = 100, message = "Title too long")
    private String title;
    @Column(name = "trailer")
    @Size(min = 11, max = 11, message = "Trailer id must be 11 characters")
    private String trailer;
    @Column(name = "image_thumbnail")
    @Pattern(regexp = "^https?://.+", message = "Wrong path of thumbnail")
    private String imageThumbnail;
    @Column(name = "size", nullable = false)
    @PositiveOrZero(message = "Cannot add negative size")
    @Digits(integer = 6, fraction = 1, message = "Size not correct")
    private Double size;
    @Column(name = "price")
    @PositiveOrZero(message = "Cannot add negative price")
    @Digits(integer = 6, fraction = 2, message = "Price not correct")
    private BigDecimal price;
    @Column(name = "description", length = 10000)
    @Length(min = 20, message = "Description too short")
    private String description;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
    @ManyToMany(targetEntity = User.class, mappedBy = "games")
    private Set<User> users;
}
