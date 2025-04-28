package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
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
        return "auth/auth-login"; // Return login page view
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request, Model model) {
        boolean success = authService.authenticate(username, password);

        if (success) {
            User user = authService.getUserByUsername(username);
            if (user != null) {
                // Lưu thông tin vào session qua HttpServletRequest
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }  // true tạo mới session nếu chưa có
                HttpSession session = request.getSession(true);
                session.setAttribute("loggedIn", true);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userId", user.getId());
                System.out.println("UserId saved to session: " + user.getId());  // Kiểm tra
                return "redirect:/trangchu";
            } else {
                model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
                return "auth/auth-login";
            }
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "auth/auth-login";
        }
        
    }

}