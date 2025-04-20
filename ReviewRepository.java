package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.pickleball_booking.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
