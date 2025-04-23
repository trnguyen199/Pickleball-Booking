package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.pickleball_booking.entity.Court;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh tại đây
}