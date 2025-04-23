package ut.edu.pickleball_booking.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ut.edu.pickleball_booking.dto.request.UserUpdateRequest;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.services.UserService;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            model.addAttribute("error", "Bạn cần đăng nhập để xem thông tin cá nhân.");
            return "redirect:/login";
        }

        String username = authentication.getName();
        System.out.println("Username: " + username);

        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
        }
        return "master/profile";
    }

    // Các phương thức khác nếu cần thiết
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updateUser, Model model) {
        // Tạo một đối tượng UserUpdateRequest từ User
        UserUpdateRequest request = new UserUpdateRequest();
        request.setFullName(updateUser.getFullName());
        request.setGender(updateUser.getGender());
        request.setDob(updateUser.getDob());
        request.setEmail(updateUser.getEmail());
        request.setPhone(updateUser.getPhone());
        request.setAddress(updateUser.getAddress());

        // Gọi phương thức updateUser với userId và request
        userService.updateUser(String.valueOf(updateUser.getId()), request);

        model.addAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/profile"; // Chuyển hướng lại trang profile
    }
}


