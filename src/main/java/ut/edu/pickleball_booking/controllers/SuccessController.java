package ut.edu.pickleball_booking.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessController {

    @GetMapping("/success")
    public String successPage() {
        return "success"; // Tên file HTML cho trang thành công
    }
}