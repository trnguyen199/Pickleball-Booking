package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.pickleball_booking.entity.Court;

public interface CourtRepository extends JpaRepository<Court, Integer> {
}
