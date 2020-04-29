package softuni.user_system.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "background_colour")
    private String backgroundColour;
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
    @ManyToMany
    @JoinTable(name = "albums_pictures",
    joinColumns = {@JoinColumn(name = "album_id")},
    inverseJoinColumns = {@JoinColumn(name = "picture_id")})
    private Set<Picture> pictures;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Album() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
