package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.pickleball_booking.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    
    Optional<User> findById(Long id);

    // Kiểm tra nếu email đã tồn tại
    Optional<User> findByUsernameOrEmail(String username, String email);
    
    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);



    Optional<User> findByUsername(String username);

   
    
}
    
