package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ut.edu.pickleball_booking.services.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @GetMapping("/lienhe")
    public String getLienhe() {
        return "public/lienhe"; // Trả về template trang liên hệ
    }

    @PostMapping("/lienhe/send")
    public String sendContactMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("content") String content,
            RedirectAttributes redirectAttributes) {

        logger.info("Nhận yêu cầu liên hệ từ: {}", email);

        try {
            // Lưu thông tin liên hệ vào bảng contacts
            contactService.saveContact(name, email, content);
            redirectAttributes.addFlashAttribute("success", "Thông tin của bạn đã được gửi thành công!");
            logger.info("Lưu thông tin liên hệ thành công từ: {}", email);
        } catch (Exception e) {
            logger.error("Lỗi khi lưu thông tin liên hệ: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi gửi tin nhắn, vui lòng thử lại sau.");
        }

        return "redirect:/lienhe"; // Chuyển hướng về trang liên hệ
    }
}