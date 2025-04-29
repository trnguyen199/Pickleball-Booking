package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ut.edu.pickleball_booking.services.UserService;
import ut.edu.pickleball_booking.entity.User;

import java.util.List;

@Controller
@RequestMapping("/admin/admin-accounts")
public class AdminAccountController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listAccounts(@RequestParam(value = "role", required = false, defaultValue = "ALL") String role, Model model) {
        List<User> users;
        if ("ROLE_OWNER".equals(role)) {
            users = userService.getUsersByRole("ROLE_OWNER");
        } else if ("ROLE_CUSTOMER".equals(role)) { // Đổi thành ROLE_CUSTOMER
            users = userService.getUsersByRole("ROLE_CUSTOMER");
        } else {
            users = userService.getAllUsers();
        }
        model.addAttribute("users", users);
        model.addAttribute("role", role);
        model.addAttribute("userCount", users.size());
        return "admin/admin-accounts";
    }

    @GetMapping("/{id}")
    public String accountDetail(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/account-detail";
    }
}