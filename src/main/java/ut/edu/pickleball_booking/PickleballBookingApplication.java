package ut.edu.pickleball_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ut.edu.pickleball_booking")
public class PickleballBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PickleballBookingApplication.class, args);
    }
}

