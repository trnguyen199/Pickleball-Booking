package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthFgPasswordController {

    @GetMapping("/forgot-password")
    public String login() {
        return "auth/auth-fg-password"; 
    }
}
