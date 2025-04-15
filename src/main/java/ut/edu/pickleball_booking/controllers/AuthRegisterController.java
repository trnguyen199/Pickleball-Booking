package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthRegisterController {

    @GetMapping("/register")
    public String login() {
        return "master/auth-register"; 
    }
}
