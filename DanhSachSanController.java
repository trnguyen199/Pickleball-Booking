package ut.edu.pickleball_booking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ut.edu.pickleball_booking.repositories.CourtRepository;

import java.util.List;

@Controller
@RequestMapping("/danhsachsan")
public class DanhSachSanController {
    private final CourtRepository repo;

    public DanhSachSanController(CourtRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String showCourts(Model model) {
        model.addAttribute("courts", repo.findAll());
        return "public/danhsachsan";
    }

    @GetMapping("/search")
    public String searchCourts(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "location", required = false) String location,
                                Model model) {
        List<Court> courts;
        if (location != null && !location.isEmpty()) {
            courts = repo.findByLocationContainingIgnoreCase(location);
        } else if (name != null && !name.isEmpty()) {
            courts = repo.findByNameContainingIgnoreCase(name);
        } else {
            courts = repo.findAll();
        }
        model.addAttribute("courts", courts);
        return "public/danhsachsan";
    }
}
