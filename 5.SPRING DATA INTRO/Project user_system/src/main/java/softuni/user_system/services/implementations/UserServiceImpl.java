package softuni.user_system.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.user_system.entities.User;
import softuni.user_system.repositories.UserRepository;
import softuni.user_system.services.UserService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers() {
        if (this.userRepository.count() !=0){
            return;
        }
        User user = new User();
        user.setFirstName("Pesho");
        user.setLastName("Peshev");
        user.setDate(LocalDate.parse("12/03/1990", DateTimeFormatter.ofPattern("d/M/yyyy")));
        user.setDateTime(LocalDateTime.parse("12/03/1990/23:00:00", DateTimeFormatter.ofPattern("d/M/yyyy/HH:mm:ss")));
        user.setEmail("pesho123@gmail.com");
        user.setUsername("pesho123");
        user.setIsDeleted(false);
        this.userRepository.saveAndFlush(user);

        user = new User();
        user.setFirstName("Gosho");
        user.setLastName("Goshev");
        user.setDate(LocalDate.parse("12/03/1990", DateTimeFormatter.ofPattern("d/M/yyyy")));
        user.setDateTime(LocalDateTime.parse("12/03/1990/23:00:00", DateTimeFormatter.ofPattern("d/M/yyyy/HH:mm:ss")));
        user.setEmail("gosho@gmail.com");
        user.setUsername("gosho_to");
        user.setIsDeleted(false);
        this.userRepository.saveAndFlush(user);

        user = new User();
        user.setFirstName("Misho");
        user.setLastName("Peshev");
        user.setDate(LocalDate.parse("12/03/1990", DateTimeFormatter.ofPattern("d/M/yyyy")));
        user.setDateTime(LocalDateTime.parse("12/03/1990/23:00:00", DateTimeFormatter.ofPattern("d/M/yyyy/HH:mm:ss")));
        user.setEmail("Mishooooo_kavala@gmail.com");
        user.setUsername("mishka_2012");
        user.setIsDeleted(false);
        this.userRepository.saveAndFlush(user);

        user = new User();
        user.setFirstName("Aneta");
        user.setLastName("Kolarova");
        user.setDate(LocalDate.parse("12/12/1990", DateTimeFormatter.ofPattern("d/M/yyyy")));
        user.setDateTime(LocalDateTime.parse("12/05/1990/23:00:00", DateTimeFormatter.ofPattern("d/M/yyyy/HH:mm:ss")));
        user.setEmail("aneta-k@yahoo.com");
        user.setUsername("ani-kiflata");
        user.setIsDeleted(false);
        this.userRepository.saveAndFlush(user);

        user = new User();
        user.setFirstName("Stoyan");
        user.setLastName("Kirev");
        user.setDate(LocalDate.parse("12/06/1990", DateTimeFormatter.ofPattern("d/M/yyyy")));
        user.setDateTime(LocalDateTime.parse("12/08/1990/23:00:00", DateTimeFormatter.ofPattern("d/M/yyyy/HH:mm:ss")));
        user.setEmail("kiflo_man@yahoo.com");
        user.setUsername("st_keep_me");
        user.setIsDeleted(false);
        this.userRepository.saveAndFlush(user);

        user = new User();
        user.setFirstName("Filiq");
        user.setLastName("Slutenica");
        user.setDate(LocalDate.parse("12/07/1990", DateTimeFormatter.ofPattern("d/M/yyyy")));
        user.setDateTime(LocalDateTime.parse("12/12/1990/23:00:00", DateTimeFormatter.ofPattern("d/M/yyyy/HH:mm:ss")));
        user.setEmail("filiq.ta@gmail.com");
        user.setUsername("fili_e_tuk");
        user.setIsDeleted(false);
        this.userRepository.saveAndFlush(user);
    }

    Scanner scanner = new Scanner(System.in);
    @Override
    public List<User> findAllByProvider() {
        List<User> users = this.userRepository.findAllByEmailContaining(scanner.nextLine());
        return users;
    }

    @Override
    public List<User> findAllInactiveAfter() {
        LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("d/M/yyyy/HH:mm:ss"));
        List<User> inactiveUsers = this.userRepository.findAllByDateTimeBefore(dateTime);
        for (User u:inactiveUsers
             ) {
            u.setIsDeleted(true);
        }
        return inactiveUsers;
    }

    @Override
    public void deleteUsers() {
       List<User> toDelete = this.userRepository.findAllByIsDeletedTrue();
        for (User u: toDelete
             ) {
            this.userRepository.delete(u);
        }
    }
}
