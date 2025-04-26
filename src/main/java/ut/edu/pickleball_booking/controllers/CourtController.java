package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.CourtRepository;
import ut.edu.pickleball_booking.services.CourtService;
import ut.edu.pickleball_booking.services.UserService;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/courts")
public class CourtController {

    private final CourtService courtService;
    private final CourtRepository courtRepository;

    @Autowired
    public CourtController(CourtService courtService, CourtRepository courtRepository) {
        this.courtService = courtService;
        this.courtRepository = courtRepository;
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
    @Autowired
    private UserService userService; // Service để tìm user theo id

    @PostMapping("/create")
    public String createCourt(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               @RequestParam("location") String location,
                               @RequestParam("ownerId") Long ownerId,
                               @RequestPart("image") MultipartFile image,
                               RedirectAttributes redirectAttributes) {
        try {
            // Lưu ảnh
            String imageUrl = null;
            if (!image.isEmpty()) {
                String uploadDir = "src/main/resources/static/assets/img/elements";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) uploadDirFile.mkdirs();
    
                String fileName = image.getOriginalFilename();
                image.transferTo(new File(uploadDir + "/" + fileName));
                imageUrl = fileName;
            }
    
            // Lấy User theo ownerId
            User owner = userService.findById(ownerId);
            if (owner == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy chủ sân.");
                return "redirect:/courts";
            }
    
            Court court = new Court();
            court.setName(name);
            court.setDescription(description);
            court.setLocation(location);
            court.setImageUrl(imageUrl);
            court.setCourtOwner(owner);
    
            courtService.saveCourt(court);
            redirectAttributes.addFlashAttribute("success", "Tạo sân thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tạo sân.");
        }
    
        return "redirect:/danhchochusan/manage-courts";
    }
    


    // Xóa sân
    @PostMapping("/delete/{id}")
    public String deleteCourt(@PathVariable Long id) {
        courtService.deleteCourt(id);
        return "redirect:/danhchochusan/manage-courts";
    }
}
