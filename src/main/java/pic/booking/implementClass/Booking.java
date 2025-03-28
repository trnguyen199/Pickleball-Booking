package pic.booking.implementClass;
import pic.booking.enums.BookingStatus;
import pic.booking.interfaces.PricingStrategy;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private User customer;
    private Court court;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
    private PricingStrategy pricingStrategy;

    public double calculateTotalPrice() {
        long hours = java.time.Duration.between(startTime, endTime).toHours();
        return pricingStrategy.calculatePrice(court.getPricePerHour(), (int) hours);
    }

    // Constructor
    public Booking(Long id, User customer, Court court, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status, PricingStrategy pricingStrategy) {
        this.id = id;
        this.customer = customer;
        this.court = court;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.pricingStrategy = pricingStrategy;
    }

    // Getter customer
    public User getCustomer() {
        return customer;
    }

    // Setter customer
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    // getter khác
    public Long getId() {
        return id;
    }

    public Court getCourt() {
        return court;
    }

    public BookingStatus getStatus() {
        return status;
    }
}