package ut.edu.pickleball_booking.dto.request;

import java.time.LocalDate;

public class BookingRequestDto {
    private Long userId;
    private Long courtId;
    private int price;
    private Long timeSlotId;
    private LocalDate bookingDate; 
    // Getter và Setter
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourtId() {
        return courtId;
    }
    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }
    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

}
