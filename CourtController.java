package ut.edu.pickleball_booking.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.CourtRepository;
import ut.edu.pickleball_booking.services.CourtService;
import ut.edu.pickleball_booking.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/courts")
public class CourtController {

    private static final Logger logger = LoggerFactory.getLogger(CourtController.class);

    private final CourtService courtService;
    private final CourtRepository courtRepository;
    private final UserService userService;

    public CourtController(CourtService courtService, CourtRepository courtRepository, UserService userService) {
        this.courtService = courtService;
        this.courtRepository = courtRepository;
        this.userService = userService;
    }

    // Endpoint lấy danh sách sân
    @GetMapping("/api")
    @ResponseBody
    public List<Court> getCourts() {
        return courtRepository.findAll();
    }

    // Trang quản lý sân (chạy trang danh sách sân)
    @GetMapping("/danhsachsan")
    public String showCourts(Model model) {
        List<Court> courts = courtRepository.findAll();
        model.addAttribute("courts", courts);
        return "danhsachsan"; 
    }

    // Tạo sân mới
    @PostMapping("/create")
    public String createCourt(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               @RequestParam("location") String location,
                               @RequestParam("ownerId") Long ownerId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            if (ownerId == null) {
                ownerId = (Long) session.getAttribute("userId");
            }

            if (ownerId == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy chủ sân.");
                return "redirect:/courts";
            }

            User owner = userService.findById(ownerId);
            if (owner == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy chủ sân.");
                return "redirect:/courts";
            }

            Court court = new Court();
            court.setName(name);
            court.setDescription(description);
            court.setLocation(location);
            court.setCourtOwner(owner);

            courtService.saveCourt(court);
            redirectAttributes.addFlashAttribute("success", "Tạo sân thành công!");
        } catch (Exception e) {
            logger.error("Error creating court: ", e);
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tạo sân.");
        }

        return "redirect:/danhchochusan/manage-courts";
    }

    // API tìm kiếm và lọc sân
    @GetMapping("/search")
    public String searchCourts(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "location", required = false) String location,
                                Model model) {
        List<Court> courts;
        if (location != null && !location.isEmpty() && name != null && !name.isEmpty()) {
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

    // Xóa sân
    @PostMapping("/delete/{id}")
    public String deleteCourt(@PathVariable Long id) {
        courtService.deleteCourt(id);
        return "redirect:/danhchochusan/manage-courts";
    }
}
