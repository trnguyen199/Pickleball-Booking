package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import ut.edu.pickleball_booking.services.AuthService;
import ut.edu.pickleball_booking.entity.User;


@Controller
public class AuthLoginController {

    private final AuthService authService;

    @Autowired
    public AuthLoginController(AuthService authService) {
        this.authService = authService;
    }


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
    //             return "redirect:/trangchu"; // Chuyển hướng đến trang chủ
    //         } else {
    //             model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
    //             return "master/auth-login"; // Quay lại trang đăng nhập
    //         }
    //     } else {
    //         model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
    //         return "master/auth-login"; // Quay lại trang đăng nhập
    //     }
    // }       
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, Model model) {
    boolean success = authService.authenticate(username, password);

    if (success) {
        User user = authService.getUserByUsername(username);
        if (user != null) {
            // Lưu thông tin vào session
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("user", user);
            return "redirect:/trangchu"; // Chuyển hướng đến trang chủ
        } else {
            model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
            return "master/auth-login"; // Quay lại trang đăng nhập
        }
    } else {
        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
        return "master/auth-login"; // Quay lại trang đăng nhập
    }
}
}