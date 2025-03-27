package ut.edu.Pickleball_Booking.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.Pickleball_Booking.models.Permission;
import ut.edu.Pickleball_Booking.repositories.PermissionRepository;
import java.util.*;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    public List<Permission>findAll(){
        return permissionRepository.findAll();
    }
    public long CreatePermission(Permission permission) {
        return permissionRepository.save(permission).getId();
    }
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }
    public Permission updatePermission(Permission permission) {
        return permissionRepository.save(permission);
    }
}
