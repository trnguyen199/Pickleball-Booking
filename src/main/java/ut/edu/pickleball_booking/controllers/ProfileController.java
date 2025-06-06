package ut.edu.pickleball_booking.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ut.edu.pickleball_booking.dto.request.UserUpdateRequest;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.entity.Booking;
import ut.edu.pickleball_booking.services.UserService;
import ut.edu.pickleball_booking.services.BookingService;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ProfileController {

    private final UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ut.edu.pickleball_booking.repositories.BookingRepository bookingRepository;

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
            // Lấy lịch sử đặt sân
            model.addAttribute("bookings", bookingService.getBookingsByUsername(username));
        } else {
            model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
        }
        return "public/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updateUser, HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("\n\n======== START PROFILE UPDATE ========");
        System.out.println("Received request to update profile for user ID: " + updateUser.getId());

        try {
            // Tạo một đối tượng UserUpdateRequest từ User
            UserUpdateRequest request = new UserUpdateRequest();
            request.setFullName(updateUser.getFullName());
            request.setGender(updateUser.getGender());
            request.setDob(updateUser.getDob());
            request.setEmail(updateUser.getEmail());
            request.setPhone(updateUser.getPhone());
            request.setAddress(updateUser.getAddress());

            System.out.println("Created UserUpdateRequest with data: " + request);

            // Gọi phương thức updateUser với userId và request
            System.out.println("Calling userService.updateUser with ID: " + updateUser.getId());
            User updatedUser = userService.updateUser(String.valueOf(updateUser.getId()), request);
            System.out.println("User update completed successfully");

            // Cập nhật thông tin session
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", updatedUser.getUsername() != null && !updatedUser.getUsername().trim().isEmpty()
                    ? updatedUser.getUsername() : updatedUser.getUsername());
            session.setAttribute("user", updatedUser);
            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
            // Chuyển hướng về trang chủ
            return "redirect:/profile"; // Sửa từ /trangchu thành / (root path)
        } catch (Exception e) {
            // Xử lý nếu có lỗi
            System.out.println("ERROR: Error updating profile: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật thông tin: " + e.getMessage());
            return "redirect:/profile";
        }
    }

    @PostMapping("/profile/review")
    public String submitReview(
            @RequestParam("bookingId") Long bookingId,
            @RequestParam("rating") Integer rating,
            @RequestParam("review") String review
    ) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setRating(rating);
        booking.setReview(review);
        bookingRepository.save(booking);
        return "redirect:/profile";
    }
}


