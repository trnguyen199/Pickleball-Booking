package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.WithdrawalHistory;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repository.WithdrawalHistoryRepository;
import java.util.List;

@Service
public class WithdrawalHistoryService {
    
    @Autowired
    private WithdrawalHistoryRepository withdrawalHistoryRepository;

    public WithdrawalHistory createWithdrawalRecord(User user, Long amount, String bankName, String accountNumber, String accountName) {
        WithdrawalHistory history = new WithdrawalHistory();
        history.setUser(user);
        history.setAmount(amount);
        history.setBankName(bankName);
        history.setAccountNumber(accountNumber);
        history.setAccountName(accountName);
        history.setStatus("Đang xử lý");
        return withdrawalHistoryRepository.save(history);
    }

    public List<WithdrawalHistory> getUserWithdrawalHistory(User user) {
        return withdrawalHistoryRepository.findByUserOrderByCreatedAtDesc(user);
    }
}