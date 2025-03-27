package ut.edu.Pickleball_Booking.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ut.edu.Pickleball_Booking.services.PermissionService;
import java.util.List;
import ut.edu.Pickleball_Booking.models.Permission;

@RestController
@RequestMapping("/per")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    //lấy dữ liệu
    @GetMapping("/permissions")
    public List<Permission> getAllPermission(){
        return permissionService.findAll();
    }
    @GetMapping("/test")
    public String test(){
        return "Hello World";
    }
    //thêm dữ liệu mới
    @PostMapping("/permission")
    public long addPermission(@RequestBody Permission permission){
        return permissionService.CreatePermission(permission);
    }
    //cập nhật dữ liệu toàn bộ
    @PutMapping("/permission")
    public Permission updatePermission(@RequestBody Permission permission){
        return permissionService.updatePermission(permission);
    }

}
