package ut.edu.pickleball_booking.configurations;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ut.edu.pickleball_booking.entity.User;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("username", user.getUsername()); // hoặc getFullName()
        } else {
            model.addAttribute("loggedIn", false);
        }
    }
}
