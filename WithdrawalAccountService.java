package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.WithdrawalAccount;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repository.WithdrawalAccountRepository;

@Service
public class WithdrawalAccountService {
    
    @Autowired
    private WithdrawalAccountRepository withdrawalAccountRepository;

    public WithdrawalAccount getOrCreateAccount(User user) {
        return withdrawalAccountRepository.findByUser(user)
            .orElseGet(() -> {
                WithdrawalAccount newAccount = new WithdrawalAccount();
                newAccount.setUser(user);
                newAccount.setBalance(0L);
                return withdrawalAccountRepository.save(newAccount);
            });
    }

    public WithdrawalAccount updateAccount(User user, String accountName, String accountNumber, String bankName) {
        WithdrawalAccount account = getOrCreateAccount(user);
        account.setAccountName(accountName);
        account.setAccountNumber(accountNumber);
        account.setBankName(bankName);
        return withdrawalAccountRepository.save(account);
    }

    public WithdrawalAccount updateBalance(User user, Long amount) {
        WithdrawalAccount account = getOrCreateAccount(user);
        account.setBalance(account.getBalance() + amount);
        return withdrawalAccountRepository.save(account);
    }

    public boolean processWithdrawal(User user, Long amount) {
        WithdrawalAccount account = getOrCreateAccount(user);
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            withdrawalAccountRepository.save(account);
            return true;
        }
        return false;
    }
}