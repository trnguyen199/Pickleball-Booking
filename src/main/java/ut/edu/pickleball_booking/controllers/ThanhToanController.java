package ut.edu.pickleball_booking.controllers;

import ut.edu.pickleball_booking.entity.Role;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.RoleRepository;
import ut.edu.pickleball_booking.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import java.util.Set;

@Controller
public class ThanhToanController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/xac-nhan-thanh-toan")
    public String xacNhanThanhToan(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        try {
            Set<Role> roles = user.getRoles();

            boolean isOwner = roles.stream().anyMatch(r -> r.getName().equals("ROLE_OWNER"));
            boolean isCustomer = roles.stream().anyMatch(r -> r.getName().equals("ROLE_CUSTOMER"));

            if (!isOwner && isCustomer) {
                // Tìm vai trò ROLE_OWNER trong cơ sở dữ liệu
                Role ownerRole = roleRepository.findByName("ROLE_OWNER")
                    .orElseThrow(() -> new RuntimeException("ROLE_OWNER not found in database"));

                // Gỡ vai trò ROLE_CUSTOMER nếu cần
                roles.removeIf(r -> r.getName().equals("ROLE_CUSTOMER"));

                // Thêm vai trò ROLE_OWNER
                roles.add(ownerRole);
                user.setRoles(roles);

                // Lưu người dùng với vai trò mới
                userRepository.save(user);

                // Cập nhật lại thông tin người dùng trong session
                session.setAttribute("user", user);
            } else if (!isOwner) {
                return "redirect:/error"; // Vai trò không hợp lệ
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/danhchochusan";
    }
}