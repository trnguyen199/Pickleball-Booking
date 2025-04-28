package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.pickleball_booking.entity.Booking;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Username(String username);
    List<Booking> findByCourt_IdAndReviewIsNotNull(Long courtId);
    List<Booking> findByCourt_IdAndReviewIsNotNullAndHiddenFalse(Long courtId);
    List<Booking> findByReviewIsNotNullAndHiddenFalse();
    
    // Tìm kiếm booking theo court owner id
    @Query("SELECT b FROM Booking b WHERE b.court.courtOwner.id = :ownerId")
    List<Booking> findByCourt_CourtOwner_Id(@Param("ownerId") Long ownerId);
    
    // Tìm kiếm booking theo court id
    List<Booking> findByCourt_Id(Long courtId);
}