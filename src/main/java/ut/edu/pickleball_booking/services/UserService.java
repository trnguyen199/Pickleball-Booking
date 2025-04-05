package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.dto.request.UserCreationRequest;
import ut.edu.pickleball_booking.dto.request.UserUpdateRequest;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired

    private UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public User updateUser(String userId , UserUpdateRequest request ) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(Long Id) {

        userRepository.deleteById(Id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

}