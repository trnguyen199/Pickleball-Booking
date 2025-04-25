package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.pickleball_booking.entity.Court;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    List<Court> findByCourtOwner_Id(Long ownerId);

}