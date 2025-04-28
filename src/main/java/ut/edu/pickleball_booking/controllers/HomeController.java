
package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user); // Truyền thông tin người dùng vào view nếu cần
        return "master/home"; // Trả về template tương ứng
    }

    @GetMapping("/trangchu")
    public String trangChu(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user); // Truyền thông tin người dùng vào view nếu cần
        return "master/home"; // Trả về template tương ứng
    }

}
