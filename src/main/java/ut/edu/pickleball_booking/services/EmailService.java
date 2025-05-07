package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mã xác nhận quên mật khẩu");
        message.setText("Mã xác nhận của bạn là: " + code);
        mailSender.send(message);
    }

    public boolean sendReplyEmail(String toEmail, String recipientName, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            
            // Tạo nội dung email với định dạng phù hợp
            String emailBody = "Kính gửi " + recipientName + ",\n\n" 
                             + content + "\n\n"
                             + "Trân trọng,\n"
                             + "Đội ngũ Pickleball Booking";
                             
            message.setText(emailBody);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            // Ghi log lỗi
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
            return false;
        }
    }
}