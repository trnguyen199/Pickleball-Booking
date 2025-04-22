package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ut.edu.pickleball_booking.dto.request.UserCreationRequest;
import ut.edu.pickleball_booking.dto.request.UserUpdateRequest;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.services.UserService;

import java.util.List;

import ut.edu.pickleball_booking.repositories.UserRepository;
import org.springframework.ui.Model;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest request) {
        // Kiểm tra xem người dùng đã tồn tại chưa
        if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Tên đăng nhập hoặc email đã tồn tại!");
        }
    
        // Tạo đối tượng User mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
    
        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);
    
        return ResponseEntity.ok("Người dùng đã được tạo thành công!");
    }

    @GetMapping
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{userId}")
    @ResponseBody
    public User updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }


    @GetMapping("/user-info")
    public String userInfo(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        model.addAttribute("user", user);
        return "user_info";
    }
}
