package ut.edu.pickleball_booking.dto;

public class WithdrawalStatus {
    private boolean success;
    private String message;
    private Long amount;
    private String status;

    public WithdrawalStatus(boolean success, String message, Long amount) {
        this.success = success;
        this.message = message;
        this.amount = amount;
        this.status = "Đang xử lý";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}