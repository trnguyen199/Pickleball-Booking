package pic.booking.services;

import pic.booking.implementClass.Court;
import pic.booking.implementClass.User;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private List<Court> availableCourts;

    public BookingService(List<Court> courts) {
        this.availableCourts = courts;
    }

    public synchronized void bookCourt(User customer, Court court, LocalDateTime startTime, LocalDateTime endTime) {
        if (!court.isAvailable()) {
            System.out.println("Sân " + court.getName() + " đã được đặt trước.");
            return;
        }

        court.setAvailable(false);
        System.out.println(customer.getUsername() + " đã đặt sân " + court.getName());

        // Giả lập thời gian xử lý booking
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        court.setAvailable(true); // Mở lại sân sau khi hết thời gian
    }
}
