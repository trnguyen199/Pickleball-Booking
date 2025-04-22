package ut.edu.pickleball_booking.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthLogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa thông tin người dùng khỏi session
        session.invalidate(); // Xóa toàn bộ session, bao gồm thông tin người dùng
        
        // Chuyển hướng về trang đăng nhập
        return "redirect:/login";
    }
}