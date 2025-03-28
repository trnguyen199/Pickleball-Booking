package pic.booking.implementClass;
import pic.booking.enums.PaymentStatus;

import java.time.LocalDateTime;

public class Payment {
    private Long id;
    private Booking booking;
    private double amount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;

}