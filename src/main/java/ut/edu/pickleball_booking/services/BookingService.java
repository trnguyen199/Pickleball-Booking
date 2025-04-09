package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.Booking;
import ut.edu.pickleball_booking.repositories.BookingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    public Booking saveBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id){
        return bookingRepository.findById(id);
    }

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }
}
