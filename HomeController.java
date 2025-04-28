package ut.edu.pickleball_booking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.repositories.CourtRepository;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private final CourtRepository courtRepository;

    public HomeController(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    @GetMapping("/trangchu")
    public String trangChu(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user); // Truyền thông tin người dùng vào view nếu cần
        return "master/home"; // Trả về template tương ứng
    }

    @GetMapping("/search")
    public String searchCourtsFromHome(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "location", required = false) String location,
                                        Model model) {
        List<Court> courts;
        if (name != null && !name.isEmpty() && location != null && !location.isEmpty()) {
            courts = courtRepository.findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location);
        } else if (location != null && !location.isEmpty()) {
            courts = courtRepository.findByLocationContainingIgnoreCase(location);
        } else if (name != null && !name.isEmpty()) {
            courts = courtRepository.findByNameContainingIgnoreCase(name);
        } else {
            courts = courtRepository.findAll();
        }
        model.addAttribute("courts", courts);
        return "public/danhsachsan";
    }
}
