package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.services.EmailService;
import ut.edu.pickleball_booking.services.UserService;

import java.util.Random;

@Controller
public class AuthFgPasswordController {

    @Autowired
    private EmailService emailService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthFgPasswordController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    private final Random random = new Random();
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "auth/auth-fg-password";
    }
    @PostMapping("/forgot-password")
    public String sendVerificationCode(@RequestParam("email") String email, HttpSession session, Model model) {
        // Tạo mã xác nhận ngẫu nhiên (6 chữ số)
        String verificationCode = String.format("%06d", new Random().nextInt(999999));

        // Lưu mã xác nhận vào session
        session.setAttribute("verificationCode", verificationCode);
        session.setAttribute("email", email);

        // Gửi mã xác nhận qua email
        emailService.sendVerificationCode(email, verificationCode);

        model.addAttribute("message", "Mã xác nhận đã được gửi đến email của bạn.");
        return "auth/auth-verify-code"; // Chuyển đến trang nhập mã xác nhận
    }
    @PostMapping("/verify-code")
    public String verifyCode(@RequestParam("code") String code, HttpSession session, Model model) {
        // Lấy mã xác nhận và email từ session
        String expectedCode = (String) session.getAttribute("verificationCode");
        String email = (String) session.getAttribute("email");

        // Log để kiểm tra giá trị
        System.out.println("Expected Code: " + expectedCode);
        System.out.println("User Input Code: " + code);

        // Kiểm tra mã xác nhận
        if (expectedCode != null && expectedCode.equals(code)) {
            model.addAttribute("email", email);
            return "auth/auth-reset-password"; // Chuyển đến trang đặt lại mật khẩu
        } else {
            model.addAttribute("error", "Mã xác nhận không đúng. Vui lòng thử lại.");
            return "auth/auth-verify-code";
        }
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("password") String password, @RequestParam("email") String email, Model model) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("error", "Email hoặc mật khẩu không được để trống.");
            return "auth/auth-reset-password";
        }
    
        User user = userService.getUserByEmail(email);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userService.updatePasswordUser(user);
            model.addAttribute("message", "Mật khẩu đã được đặt lại thành công.");
            return "auth/auth-login";
        } else {
            model.addAttribute("error", "Không tìm thấy người dùng với email này.");
            return "auth/auth-reset-password";
        }
    }
}