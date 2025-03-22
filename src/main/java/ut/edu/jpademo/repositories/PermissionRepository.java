package ut.edu.jpademo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  ut.edu.jpademo.models.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
