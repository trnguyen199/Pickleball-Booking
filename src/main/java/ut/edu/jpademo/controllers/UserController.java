package ut.edu.jpademo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ut.edu.jpademo.models.User;
import ut.edu.jpademo.services.UserService;

import java.util.Optional;
@RestController
@RequestMapping("/api/users")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);  // Sử dụng đúng tên hàm
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.save(user);  // Sử dụng đúng tên hàm
        return ResponseEntity.ok("User created successfully!");
    }
}
