package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ut.edu.pickleball_booking.services.ContactService;
import ut.edu.pickleball_booking.services.EmailService;

@Controller
public class AdminContactController {

    private final ContactService contactService;
    private final EmailService emailService;

    @Autowired
    public AdminContactController(ContactService contactService, EmailService emailService) {
        this.contactService = contactService;
        this.emailService = emailService;
    }


    
    @GetMapping("/admin/admin-contact")
    public String adminContact(Model model) {
        // Lấy danh sách liên hệ từ service và thêm vào model
        model.addAttribute("title", "Quản lý liên hệ");
        model.addAttribute("contacts", contactService.getAllContacts());
        return "admin/admin-contact"; // Trả về template cho trang quản lý liên hệ
    }
    
    @PostMapping("/admin/admin-contact/reply")
    public String sendReply(@RequestParam("contactId") Long contactId,
                           @RequestParam("recipientEmail") String recipientEmail,
                           @RequestParam("recipientName") String recipientName,
                           @RequestParam("subject") String subject,
                           @RequestParam("content") String content,
                           RedirectAttributes redirectAttributes) {
        
        // Gửi email phản hồi
        boolean success = emailService.sendReplyEmail(recipientEmail, recipientName, subject, content);
        
        if (success) {
            // Cập nhật trạng thái đã phản hồi nếu cần
            // contactService.markAsReplied(contactId);
            
            redirectAttributes.addAttribute("success", "true");
            // redirectAttributes.addFlashAttribute("success", "Email phản hồi đã được gửi thành công!");
            return "redirect:/admin/admin-contact";
        } else {
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/admin/admin-contact";
        }
    }
}