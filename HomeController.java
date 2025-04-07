package com.pickleball;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("serverTime", new Date());
        System.out.println("Trang chủ được truy cập lúc: " + new Date());
        return "index";
    }
    
    @GetMapping("/api/status")
    @ResponseBody
    public String status() {
        return "Server is running! Time: " + new Date();
    }
}
