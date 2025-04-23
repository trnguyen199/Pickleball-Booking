package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import ut.edu.pickleball_booking.dto.request.UserCreationRequest;
import ut.edu.pickleball_booking.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/auth") // Định nghĩa tiền tố cho các endpoint
public class AuthRegisterController {

    private final UserService userService;

    @Autowired
    public AuthRegisterController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/register")
    public String registerForm() {
        return "auth/auth-register"; // Trả về file HTML trong thư mục templates/auth
    }
    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute UserCreationRequest request, Model model) {
        try {
            userService.createUser(request);
            model.addAttribute("success", "Đăng ký thành công!");
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập
        } catch (Exception e) {
            model.addAttribute("error", "Đăng ký thất bại: " + e.getMessage());
            return "auth/auth-register"; // Quay lại trang đăng ký nếu có lỗi
        }
    }
}