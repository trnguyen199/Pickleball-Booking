package pic.booking.threads;
import pic.booking.services.BookingService;
import pic.booking.implementClass.Court;
import pic.booking.implementClass.User;

import java.time.LocalDateTime;

public class BookingThread implements Runnable {
    private BookingService bookingService;
    private User customer;
    private Court court;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public BookingThread(BookingService bookingService, User customer, Court court, LocalDateTime startTime, LocalDateTime endTime) {
        this.bookingService = bookingService;
        this.customer = customer;
        this.court = court;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void run() {
        bookingService.bookCourt(customer, court, startTime, endTime);
    }
}
