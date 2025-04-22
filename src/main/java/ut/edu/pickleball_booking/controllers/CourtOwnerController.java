package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourtOwnerController {

    @GetMapping("/register-owner")
    public String showRegisterPage() {
        return "register-owner"; // trả về trang HTML form đăng ký
    }

    @PostMapping("/register-owner")
    public String handleRegister(
            @RequestParam("plan") String plan,
            Model model
    ) {
        // Gắn tên gói cước đẹp để hiển thị
        String planName;
        String price;
        switch (plan) {
            case "1_thang":
                planName = "1 tháng";
                price = "300.000 VND";
                break;
            case "3_thang":
                planName = "3 tháng";
                price = "850.000 VND";
                break;
            case "6_thang":
                planName = "6 tháng";
                price = "1.600.000 VND";
                break;
            case "1_nam":
                planName = "1 năm";
                price = "3.000.000 VND";
                break;
            default:
                planName = "Không xác định";
                price = "-";
        }

        model.addAttribute("planName", planName);
        model.addAttribute("price", price);
        return "/master/register-success"; // trả về trang xác nhận đăng ký
    }
}
