package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.Withdrawal;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repository.WithdrawalRepository;

@Service
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    public Withdrawal createWithdrawal(User user, String accountName, String accountNumber, String bankName, Long amount) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setUser(user);
        withdrawal.setAccountName(accountName);
        withdrawal.setAccountNumber(accountNumber);
        withdrawal.setBankName(bankName);
        withdrawal.setAmount(amount);
        return withdrawalRepository.save(withdrawal);
    }
}