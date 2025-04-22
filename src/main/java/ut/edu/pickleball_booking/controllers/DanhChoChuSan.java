package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ut.edu.pickleball_booking.entity.Role;
import org.springframework.ui.Model;
import ut.edu.pickleball_booking.services.UserService;

@Controller
public class DanhChoChuSan {
    private final UserService userService;

    @Autowired
    public DanhChoChuSan(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/danhchochusan")
    public String getDanhSachSan(Model model, Principal principal) {
        if (principal == null) {
            System.out.println("Principal is null. Redirecting to dangkychusan.");
            return "master/danhchochusan";
        }
    
        String username = principal.getName(); // Lấy username của user đã đăng nhập
        System.out.println("Logged-in username: " + username);
    
        List<Role> roles = userService.getRolesByUsername(username);
        if (roles == null || roles.isEmpty()) {
            System.out.println("No roles found for username: " + username);
        } else {
            System.out.println("Roles: " + roles);
        }
    
        model.addAttribute("roles", roles); // Thêm danh sách role vào model
        return "master/danhchochusan";
    }
}
