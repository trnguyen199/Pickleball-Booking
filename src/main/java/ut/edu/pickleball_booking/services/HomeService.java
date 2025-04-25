package ut.edu.pickleball_booking.services;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    public String getWelcomeMessage() {
        return "Welcome to the Pickleball Booking System";
    }
}
