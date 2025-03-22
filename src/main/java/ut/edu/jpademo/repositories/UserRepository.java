package ut.edu.jpademo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.jpademo.models.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
