package ut.edu.pickleball_booking.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import ut.edu.pickleball_booking.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.Role;
import org.springframework.ui.Model;
import ut.edu.pickleball_booking.services.UserService;
import ut.edu.pickleball_booking.services.CourtService;


@Controller
public class DanhChoChuSanController {
    private final UserService userService;
    private final CourtService courtService;

    @Autowired
    public DanhChoChuSanController(UserService userService, CourtService courtService) {
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
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu không có vai trò
        }
    
        // Kiểm tra vai trò của người dùng
        boolean isCustomer = roles.stream().anyMatch(role -> role.getName().equals("ROLE_CUSTOMER"));
        boolean isOwner = roles.stream().anyMatch(role -> role.getName().equals("ROLE_OWNER"));
    
        if (isCustomer) {
            System.out.println("User is a customer. Redirecting to registration page.");
            return "public/dangkychusan"; // Chuyển hướng đến trang đăng ký làm chủ sân
        } else if (isOwner) {
            System.out.println("User is an owner. Accessing owner page.");
            model.addAttribute("roles", roles); // Thêm danh sách vai trò vào model
            try {
                User user = userService.findByUsername(username);
                model.addAttribute("user", user); // Thêm thông tin người dùng vào model
            } catch (Exception e) {
                System.out.println("Error fetching user details: " + e.getMessage());
                model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
            }
            return "manage/manage-statistics"; // Trả về template cho chủ sân
        }
    
        // Nếu không thuộc vai trò nào, yêu cầu đăng nhập
        System.out.println("User has no valid role. Redirecting to login page.");
        return "redirect:/login";
    }

    @PostMapping("/danhchochusan/thanhtoan")
    public String handlePayment(@RequestParam("plan") String plan, Model model) {
        System.out.println("Selected plan: " + plan);
    
        // Thêm dữ liệu vào model để hiển thị trên trang thanhtoan.html
        model.addAttribute("selectedPlan", plan);
    
        // Chuyển hướng đến trang thanh toán
        return "master/thanhtoan";

    }

    @GetMapping("/danhchochusan/update-info")
    public String updateInfo(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("roles", userService.getRolesByUsername(username));
        return "manage/update-info"; 
    }

    @GetMapping("/danhchochusan/manage-courts")
    public String manageCourts(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Court> courts = courtService.getCourtsByOwnerId(userId);
        model.addAttribute("courts", courts);
        model.addAttribute("userId", userId);

        return "manage/manage-courts";
    }



    @GetMapping("/danhchochusan/manage-timeslots")
    public String manageTimeslots() {
        return "manage/manage-timeslots"; // Tên template cho trang quản lý khung giờ
    }

    @GetMapping("/danhchochusan/bookings")
    public String bookings() {
        return "manage/manage-booking"; // Tên template cho trang quản lý đặt sân
    }

    @GetMapping("/danhchochusan/statistics")
    public String statistics() {
        return "manage/manage-statistics"; // Tên template cho trang thống kê
    }

    @GetMapping("/danhchochusan/reviews")
    public String reviews() {
        return "manage/manage-reviews"; // Tên template cho trang đánh giá
    }

    @GetMapping("/danhchochusan/withdrawals")
    public String withdrawals() {
        return "manage/manage-withdrawals"; // Tên template cho trang rút tiền
    }
}
