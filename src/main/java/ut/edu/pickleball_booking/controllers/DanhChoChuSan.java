package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import java.security.Principal;
import java.util.List;
import ut.edu.pickleball_booking.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import ut.edu.pickleball_booking.entity.Role;
import org.springframework.ui.Model;
import ut.edu.pickleball_booking.services.UserService;
import ut.edu.pickleball_booking.services.CourtService;


@Controller
public class DanhChoChuSan {
    private final UserService userService;
    private final CourtService courtService;

    @Autowired
    public DanhChoChuSan(UserService userService, CourtService courtService) {
        this.userService = userService;
        this.courtService = courtService;
    }
    
   @GetMapping("/danhchochusan")
    public String getDanhSachSan(Model model, Principal principal, HttpSession session) {
    if (principal == null) {
        System.out.println("Principal is null. Redirecting to login page.");
        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập
    }

    // Lấy username của người dùng đã đăng nhập
    String username = principal.getName();
    System.out.println("Logged-in username: " + username);

    // Lấy danh sách vai trò của người dùng
    List<Role> roles = userService.getRolesByUsername(username);
    if (roles == null || roles.isEmpty()) {
        System.out.println("No roles found for username: " + username);
        model.addAttribute("error", "Không tìm thấy vai trò nào cho người dùng.");
    } else {
        System.out.println("Roles: " + roles);
        model.addAttribute("roles", roles); // Thêm danh sách vai trò vào model
    }

    // Lấy thông tin người dùng từ UserService
    try {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user); // Thêm thông tin người dùng vào model
    } catch (Exception e) {
        System.out.println("Error fetching user details: " + e.getMessage());
        model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
    }

    return "master/danhchochusan"; // Trả về template
    }

    @GetMapping("/danhchochusan/update-info")
    public String updateInfo(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("roles", userService.getRolesByUsername(username));
        return "master/update-info"; // Tên template cho trang cập nhật thông tin
    }

    @GetMapping("/danhchochusan/manage-courts")
    public String manageCourts(Model model) {
        model.addAttribute("courts", courtService.getAllCourts());
        return "master/manage-courts"; // Tên template cho trang quản lý sân
    }

    @GetMapping("/danhchochusan/manage-timeslots")
    public String manageTimeslots() {
        return "master/manage-timeslots"; // Tên template cho trang quản lý khung giờ
    }

    @GetMapping("/danhchochusan/bookings")
    public String bookings() {
        return "master/bookings"; // Tên template cho trang quản lý đặt sân
    }

    @GetMapping("/danhchochusan/statistics")
    public String statistics() {
        return "master/statistics"; // Tên template cho trang thống kê
    }

    @GetMapping("/danhchochusan/reviews")
    public String reviews() {
        return "master/reviews"; // Tên template cho trang đánh giá
    }

    @GetMapping("/danhchochusan/withdrawals")
    public String withdrawals() {
        return "master/withdrawals"; // Tên template cho trang rút tiền
    }
}
