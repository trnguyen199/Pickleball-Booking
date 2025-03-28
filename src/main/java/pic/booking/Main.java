package pic.booking;
import pic.booking.enums.Role;
import pic.booking.implementClass.*;
import pic.booking.services.BookingManager;
import pic.booking.services.BookingService;
import pic.booking.threads.BookingThread;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BookingService bookingService = new BookingService(List.of(new Court(1L, "Sân A", true), new Court(2L, "Sân B", true)));
        BookingManager manager = new BookingManager();

        //test
        User user1 = new User(1L, "Nguyen", "123456", "nguyen@example.com", "0123456789", Role.CUSTOMER);
        User user2 = new User(2L, "Trung", "abcdef", "trung@example.com", "0987654321", Role.CUSTOMER);


        manager.submitBooking(new BookingThread(bookingService, user1, new Court(1L, "Sân A", true), LocalDateTime.now(), LocalDateTime.now().plusHours(2)));
        manager.submitBooking(new BookingThread(bookingService, user2, new Court(1L, "Sân A", true), LocalDateTime.now(), LocalDateTime.now().plusHours(2)));

        manager.shutdown();
    }
}