package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.pickleball_booking.entity.Booking;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Username(String username);
    List<Booking> findByCourt_IdAndReviewIsNotNull(Long courtId);
    List<Booking> findByCourt_IdAndReviewIsNotNullAndHiddenFalse(Long courtId);
    List<Booking> findByReviewIsNotNullAndHiddenFalse();
}