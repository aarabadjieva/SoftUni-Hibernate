package softuni.user_system.entities;

import softuni.user_system.anotations.Password;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", nullable = false)
    @Size(min = 4, max = 30)
    private String username;
    @Password
    private String password;
    @Email(regexp = "^([A-Za-z0-9]+)([A-Za-z0-9\\.\\-\\_])+([A-Za-z0-9])@([A-Za-z0-9]+)([A-Za-z0-9\\.\\-\\_])+\\.([A-Za-z0-9]+)([A-Za-z0-9])")
    private String email;
    @Column(name = "registered_on", nullable = false)
    private LocalDate date;
    @Column(name = "last_time_logged_in")
    private LocalDateTime dateTime;
    @Column(name = "age", nullable = false)
    @Size(min = 1, max = 120)
    private int age;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "home_town_id", referencedColumnName = "id")
    private Town bornTown;
    @ManyToOne
    @JoinColumn(name = "living_town_id", referencedColumnName = "id")
    private Town livingTown;
    @ManyToMany
    @JoinTable(name = "users_friends",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "friend_id")})
    private Set<User> friends;
    @OneToMany(mappedBy = "user", targetEntity = Album.class)
    private Set<Album> albums;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    public Town getLivingTown() {
        return livingTown;
    }

    public void setLivingTown(Town livingTown) {
        this.livingTown = livingTown;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public String getFullName(){
        return this.getFirstName() + " " + this.getLastName();
    }
}
