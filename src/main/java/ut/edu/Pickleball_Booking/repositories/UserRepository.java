package ut.edu.Pickleball_Booking.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.Pickleball_Booking.models.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
