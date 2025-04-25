package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// @RequestMapping("/booking")
public class DieuKhoanController {

    @GetMapping("/dieukhoan")
    public String getDieukhoan() {
        return "master/dieukhoan"; 
    }
}
