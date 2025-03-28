package pic.booking.interfaces;

public interface PricingStrategy {
    double calculatePrice(double basePrice, int hours);
}
