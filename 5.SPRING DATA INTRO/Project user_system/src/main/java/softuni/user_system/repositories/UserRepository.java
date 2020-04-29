package softuni.user_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.user_system.entities.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

List<User> findAllByEmailContaining(String provider);
List<User> findAllByDateTimeBefore(LocalDateTime date);
List<User> findAllByIsDeletedTrue();
}
