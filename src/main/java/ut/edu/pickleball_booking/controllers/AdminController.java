package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        // Thêm dữ liệu cần thiết vào model nếu cần
        model.addAttribute("title", "Admin Dashboard");
        return "admin/admin-dashboard"; // Trả về template cho trang dashboard
    }

    @GetMapping("/admin/manage-users")
    public String manageUsers(Model model) {
        // Thêm dữ liệu cần thiết vào model nếu cần
        model.addAttribute("title", "Quản lý người dùng");
        return "admin/manage-users"; // Trả về template cho trang quản lý người dùng
    }

    @GetMapping("/admin/manage-court-owners")
    public String manageCourts(Model model) {
        // Thêm dữ liệu cần thiết vào model nếu cần
        model.addAttribute("title", "Quản lý sân");
        return "admin/manage-court-owners"; // Trả về template cho trang quản lý sân
    }
}