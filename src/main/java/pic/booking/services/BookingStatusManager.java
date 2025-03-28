package pic.booking.services;

import pic.booking.enums.BookingStatus;

import java.util.EnumSet;

public class BookingStatusManager {
    private EnumSet<BookingStatus> validStatuses = EnumSet.allOf(BookingStatus.class);

    public boolean isValidStatus(BookingStatus status) {
        return validStatuses.contains(status);
    }

    public void printStatuses() {
        for (BookingStatus status : validStatuses) {
            System.out.println(status);
        }
    }
}
