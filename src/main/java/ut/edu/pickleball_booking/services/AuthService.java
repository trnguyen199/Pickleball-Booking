package ut.edu.pickleball_booking.services;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username).orElse(null);
    
        if (user == null) {
            System.out.println("User not found: " + username);
            return false; 
        }
    
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encoded password from DB: " + user.getPassword());
    
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username, username);
        return userOptional.orElse(null);  
    }

    public boolean saveUser(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            return false; 
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    
}
