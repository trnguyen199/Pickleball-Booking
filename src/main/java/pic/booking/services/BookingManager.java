package pic.booking.services;

import pic.booking.implementClass.Booking;
import pic.booking.implementClass.User;
import pic.booking.threads.BookingThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookingManager {
    private List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getCustomer().equals(user)) {
                result.add(b);
            }
        }
        return result;
    }

    private static final int THREAD_POOL_SIZE = 5; // giới hạn 5 luồng hoạt động cùng lúc
    private ExecutorService executor;

    public BookingManager() {
        executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void submitBooking(BookingThread task) {
        executor.execute(task);
    }

    public void shutdown() {
        executor.shutdown();
    }
}

