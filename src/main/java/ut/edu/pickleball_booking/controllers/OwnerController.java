package ut.edu.pickleball_booking.controllers;


import ut.edu.pickleball_booking.entity.CourtOwner;
import ut.edu.pickleball_booking.repositories.CourtOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OwnerController {

    @Autowired
    private CourtOwnerRepository ownerRepo;

    // Hiển thị form thông tin chủ sân
    @GetMapping("/owner-info")
    public String showOwnerInfo(@RequestParam("email") String email, Model model) {
        CourtOwner owner = ownerRepo.findByEmail(email).orElse(new CourtOwner());
        model.addAttribute("owner", owner);
        return "owner-info";
    }

    // Xử lý form cập nhật thông tin
    @PostMapping("/update-owner")
    public String updateOwner(@ModelAttribute CourtOwner owner) {
        ownerRepo.save(owner);  // Lưu hoặc cập nhật thông tin
        return "redirect:/owner-info?email=" + owner.getEmail();
    }
}
