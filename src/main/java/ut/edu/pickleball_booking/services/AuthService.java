package ut.edu.pickleball_booking.services;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    // @Autowired
    private UserRepository userRepository;  // Inject UserRepository
    // private final BCryptPasswordEncoder passwordEncoder;
    // public AuthService(BCryptPasswordEncoder passwordEncoder) {
    //     this.passwordEncoder = passwordEncoder;
    // }

    // public String encodePassword(String rawPassword) {
    //     return passwordEncoder.encode(rawPassword); // Mã hóa mật khẩu
    // }

    // public boolean matches(String rawPassword, String encodedPassword) {
    //     return passwordEncoder.matches(rawPassword, encodedPassword); // Kiểm tra mật khẩu
    // }

    
    // Method to authenticate the user
    
    public boolean authenticate(String username, String rawPassword) {
        // Bỏ qua xác thực, luôn trả về true
        return true;
    }
    // public boolean authenticate(String username, String rawPassword) {
    //     User user = userRepository.findByUsername(username)
    //             .orElse(null);

    //     if (user == null) {
    //         return false; // Người dùng không tồn tại
    //     }
    //     System.out.println("Raw password: " + rawPassword);
    //     System.out.println("Encoded password from DB: " + user.getPassword());
    //     // So sánh mật khẩu đã mã hóa trong cơ sở dữ liệu với mật khẩu người dùng nhập
    //     return true;
    //     // return passwordEncoder.matches(rawPassword, user.getPassword());
    // }

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username, username);
        return userOptional.orElse(null);  
    }

    public boolean saveUser(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            return false; 
        }
        // user.setPassword(encodePassword(user.getPassword()));
        userRepository.save(user); // Lưu người dùng vào database
        return true; // Đăng ký thành công
    }
    
}
