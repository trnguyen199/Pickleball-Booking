package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vnpay")
public class VnpayViewController {

    @GetMapping("/success")
    public String showSuccess() {
        return "vnpay/success";
    }

}
