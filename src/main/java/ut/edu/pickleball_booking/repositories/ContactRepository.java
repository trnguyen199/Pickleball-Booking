package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.pickleball_booking.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Các phương thức truy vấn có thể được thêm khi cần
}