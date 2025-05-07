package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ut.edu.pickleball_booking.entity.Contact;
import ut.edu.pickleball_booking.repositories.ContactRepository;

@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    public void saveContact(String name, String email, String content) {
        try {
            Contact contact = new Contact(name, email, content);
            contactRepository.save(contact);
            logger.info("Đã lưu thông tin liên hệ từ {} vào database", email);
        } catch (Exception e) {
            logger.error("Lỗi khi lưu thông tin liên hệ vào database: {}", e.getMessage());
        }
    }

     public List<Contact> getAllContacts() {
        logger.info("Lấy danh sách tất cả thông tin liên hệ");
        return contactRepository.findAll();
    }
}