package ut.edu.pickleball_booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.pickleball_booking.entity.WithdrawalAccount;
import ut.edu.pickleball_booking.entity.User;
import java.util.Optional;

public interface WithdrawalAccountRepository extends JpaRepository<WithdrawalAccount, Long> {
    Optional<WithdrawalAccount> findByUser(User user);
}