package ut.edu.pickleball_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.pickleball_booking.entity.Withdrawal;
import ut.edu.pickleball_booking.entity.User;
import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByUser(User user);
}