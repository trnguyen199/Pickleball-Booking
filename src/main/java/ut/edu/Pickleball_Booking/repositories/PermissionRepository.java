package ut.edu.Pickleball_Booking.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  ut.edu.Pickleball_Booking.models.Permission;
import java.util.*;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAll();

}
