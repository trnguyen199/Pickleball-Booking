
package ut.edu.pickleball_booking.repositories;

import ut.edu.pickleball_booking.entity.Role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.username = :username")
    List<Role> findByUsername(@Param("username") String username);

    Optional<Role> findByName(String name);
}
