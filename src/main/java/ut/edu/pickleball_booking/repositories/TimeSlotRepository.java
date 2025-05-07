package ut.edu.pickleball_booking.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.pickleball_booking.entity.TimeSlot;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByCourtId(Long courtId);
    TimeSlot findByCourtIdAndTime(Long courtId, String time);
}
