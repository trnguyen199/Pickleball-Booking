package ut.edu.pickleball_booking.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import ut.edu.pickleball_booking.services.AuthService;
import ut.edu.pickleball_booking.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AuthLoginController {

    // @Autowired
    // // private AuthService authService;  // Inject AuthService

    // private static final Logger logger = LoggerFactory.getLogger(AuthLoginController.class);

    @GetMapping("/login")
    public String loginForm() {
        return "master/auth-login"; // Return login page view
    }

    // @GetMapping("/")
    // public String home(Model model, Principal principal) {
    //     boolean loggedIn = (principal != null); // Kiểm tra xem người dùng đã đăng nhập chưa
    //     model.addAttribute("loggedIn", loggedIn); // Thêm vào model
    //     return "master/home"; 
    // }

    // @PostMapping("/login")
    // public String loginUser(@RequestParam String username,
    //                         @RequestParam String password,
    //                         HttpSession session, Model model) {
    //     boolean success = authService.authenticate(username, password);
    
    //     if (success) {
    //         User user = authService.getUserByUsername(username);
    //         if (user != null) {
    //             session.setAttribute("user", user);
    //             return "redirect:/trangchu";
    //         } else {
    //             model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
    //             return "master/auth-login";
    //         }
    //     } else {
    //         model.addAttribute("error", "Invalid username or password!");
    //         return "master/auth-login";
    //     }
    // }
}