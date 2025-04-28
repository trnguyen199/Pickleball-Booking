package ut.edu.pickleball_booking.services;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.dto.request.UserCreationRequest;
import ut.edu.pickleball_booking.dto.request.UserUpdateRequest;
import ut.edu.pickleball_booking.entity.Role;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.RoleRepository;
import ut.edu.pickleball_booking.repositories.UserRepository;


@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
        user.setAgreedTerms(request.isTerms());

        // Gán vai trò mặc định
        Role defaultRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new IllegalArgumentException("Vai trò mặc định không tồn tại"));
        user.getRoles().add(defaultRole);

        return userRepository.save(user);
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, UserUpdateRequest request) {
        // Tìm người dùng theo ID
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));

        // Cập nhật thông tin người dùng
        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setDob(request.getDob());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        System.out.println("Email được truyền vào: " + email);
        return userRepository.findByEmail(email).orElse(null); // Trả về null nếu không tìm thấy
    }

    public void updatePasswordUser(User user) {
        userRepository.save(user); // Lưu thông tin người dùng đã cập nhật
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<Role> getRolesByUsername(String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username is null or empty.");
            return Collections.emptyList();
        }
        System.out.println("Fetching roles for username: " + username);
        List<Role> roles = roleRepository.findByUsername(username);
        System.out.println("Roles fetched from database: " + roles);
        return roles;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng với tên đăng nhập: " + username));
    }

    public User findById(Long ownerId) {
        return userRepository.findById(ownerId).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}

