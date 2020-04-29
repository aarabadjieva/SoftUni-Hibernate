package softuni.user_system.services;

import softuni.user_system.entities.User;

import java.util.List;

public interface UserService {

    void seedUsers();
    List<User> findAllByProvider();
    List<User> findAllInactiveAfter();
    void deleteUsers();
}
