package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.CourtRepository;
import ut.edu.pickleball_booking.services.CourtService;
import ut.edu.pickleball_booking.services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/courts")
public class CourtController {

    private final CourtService courtService;
    private final CourtRepository courtRepository;
    
    private final String UPLOAD_DIR = "src/main/resources/static/assets/img/elements/";

    @Autowired
    public CourtController(CourtService courtService, CourtRepository courtRepository) {
        this.courtService = courtService;
        this.courtRepository = courtRepository;
        
        // Đảm bảo thư mục upload tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
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
                               @RequestParam(value = "image", required = false) MultipartFile image,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            // Xác định ownerId nếu chưa được cung cấp
            if (ownerId == null) {
                ownerId = (Long) session.getAttribute("userId"); // lấy userId từ session
            }
    
            // Kiểm tra xem ownerId có hợp lệ không
            if (ownerId == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy chủ sân.");
                return "redirect:/courts";
            }
    
            // Lấy User theo ownerId
            User owner = userService.findById(ownerId);
            if (owner == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy chủ sân.");
                return "redirect:/courts";
            }
    
            // Tạo đối tượng Court và thiết lập các giá trị
            Court court = new Court();
            court.setName(name);
            court.setDescription(description);
            court.setLocation(location);
            court.setCourtOwner(owner); // gán owner cho court
            
            // Xử lý upload hình ảnh
            if (image != null && !image.isEmpty()) {
                try {
                    // Lấy tên file gốc thay vì tạo UUID
                    String originalFilename = image.getOriginalFilename();
                    
                    // Tạo đường dẫn đầy đủ tới file
                    Path targetLocation = Paths.get(UPLOAD_DIR + originalFilename);
                    
                    // Copy file vào thư mục đích
                    Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                    
                    // Lưu tên file vào đối tượng Court
                    court.setImageUrl(originalFilename);
                    
                    System.out.println("Đã lưu hình ảnh: " + originalFilename);
                } catch (IOException e) {
                    System.err.println("Lỗi khi lưu hình ảnh: " + e.getMessage());
                    e.printStackTrace();
                }
            }
    
            System.out.println("Court Name: " + court.getName());
            System.out.println("Court Location: " + court.getLocation());
            System.out.println("Court Description: " + court.getDescription());
            System.out.println("Court Image: " + court.getImageUrl());
            System.out.println("Court Owner: " + (court.getCourtOwner() != null ? court.getCourtOwner().getId() : "null"));
    
            // Lưu thông tin sân
            courtService.saveCourt(court);
    
            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("success", "Tạo sân thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi tạo sân: " + e.getMessage());
        }
    
        // Chuyển hướng đến trang quản lý sân
        return "redirect:/danhchochusan/manage-courts";
    }
    
    // Hiển thị form chỉnh sửa sân
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Court court = courtRepository.findById(id).orElse(null);
        
        if (court == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sân cần chỉnh sửa");
            return "redirect:/danhchochusan/manage-courts";
        }
        
        model.addAttribute("court", court);
        return "manage/edit-court";
    }
    
    @PostMapping("/update/{id}")
    public String updateCourt(@PathVariable Long id, 
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("location") String location,
                              @RequestParam(value = "image", required = false) MultipartFile image,
                              RedirectAttributes redirectAttributes) {
        try {
            Court court = courtRepository.findById(id).orElse(null);
            if (court == null) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy sân cần cập nhật");
                return "redirect:/danhchochusan/manage-courts";
            }
            
            // Cập nhật các thông tin cơ bản
            court.setName(name);
            court.setDescription(description);
            court.setLocation(location);
            
            // Xử lý upload hình ảnh mới (nếu có)
            if (image != null && !image.isEmpty()) {
                try {
                    // Xóa hình ảnh cũ nếu có
                    if (court.getImageUrl() != null && !court.getImageUrl().isEmpty()) {
                        File oldFile = new File(UPLOAD_DIR + court.getImageUrl());
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    
                    // Lưu hình ảnh mới với tên file gốc
                    String originalFilename = image.getOriginalFilename();
                    
                    Path targetLocation = Paths.get(UPLOAD_DIR + originalFilename);
                    Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                    
                    court.setImageUrl(originalFilename);
                } catch (IOException e) {
                    System.err.println("Lỗi khi cập nhật hình ảnh: " + e.getMessage());
                }
            }
            
            // Lưu các thay đổi
            courtService.saveCourt(court);
            redirectAttributes.addFlashAttribute("success", "Cập nhật sân thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật sân: " + e.getMessage());
        }
        
        return "redirect:/danhchochusan/manage-courts";
    }

    // Xóa sân
    @PostMapping("/delete/{id}")
    public String deleteCourt(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Xóa hình ảnh sân trước khi xóa sân
            Court court = courtRepository.findById(id).orElse(null);
            if (court != null && court.getImageUrl() != null && !court.getImageUrl().isEmpty()) {
                File imageFile = new File(UPLOAD_DIR + court.getImageUrl());
                if (imageFile.exists()) {
                    imageFile.delete();
                }
            }
            
            // Xóa sân
            courtService.deleteCourt(id);
            redirectAttributes.addFlashAttribute("success", "Xóa sân thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa sân: " + e.getMessage());
        }
        
        return "redirect:/danhchochusan/manage-courts";
    }
}
