package softuni.user_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.user_system.entities.User;
import softuni.user_system.services.UserService;

import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsers();
        List<User> usersByProvider = this.userService.findAllByProvider();
        if (usersByProvider.isEmpty()){
            System.out.println("No users found with this email domain");
        }else {
            for (User u : usersByProvider
            ) {
                System.out.println(u.getUsername() + " " + u.getEmail());
            }
        }
        List<User> inactiveUsers = this.userService.findAllInactiveAfter();
        System.out.println(inactiveUsers.size()+" users have been deleted");
        this.userService.deleteUsers();
    }
}
