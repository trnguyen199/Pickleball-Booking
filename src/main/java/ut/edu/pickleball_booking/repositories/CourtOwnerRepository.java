package ut.edu.pickleball_booking.repositories;


import ut.edu.pickleball_booking.entity.CourtOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourtOwnerRepository extends JpaRepository<CourtOwner, Long> {
    Optional<CourtOwner> findByEmail(String email);
}
