package ut.edu.pickleball_booking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ut.edu.pickleball_booking.repositories.CourtRepository;

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

}
