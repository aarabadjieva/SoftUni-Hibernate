package softuni.user_system.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "caption")
    private String caption;
    @Column(name = "path", nullable = false)
    private String path;
    @ManyToMany(mappedBy = "pictures")
    private Set<Album> albums;

    public Picture() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
