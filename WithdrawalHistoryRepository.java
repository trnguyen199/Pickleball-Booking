package ut.edu.pickleball_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.pickleball_booking.entity.WithdrawalHistory;
import ut.edu.pickleball_booking.entity.User;
import java.util.List;

public interface WithdrawalHistoryRepository extends JpaRepository<WithdrawalHistory, Long> {
    List<WithdrawalHistory> findByUserOrderByCreatedAtDesc(User user);
}