package ut.edu.pickleball_booking.dto.request;

public class PaymentRequestDTO {
    private long amount;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
