package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.Booking;
import ut.edu.pickleball_booking.repositories.BookingRepository;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookingsByUsername(String username) {
        return bookingRepository.findByUser_Username(username);
    }

    public List<Booking> getReviewsByCourtId(Long courtId) {
        return bookingRepository.findByCourt_IdAndReviewIsNotNull(courtId);
    }

    public List<Booking> getReviewsByCourtIdNotHidden(Long courtId) {
        return bookingRepository.findByCourt_IdAndReviewIsNotNullAndHiddenFalse(courtId);
    }

    public List<Booking> getAllReviewsNotHidden() {
        return bookingRepository.findByReviewIsNotNullAndHiddenFalse();
    }

    public void saveReview(Long bookingId, String review) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setReview(review);
        bookingRepository.save(booking);
    }

    public void saveReviewAndRating(Long bookingId, String review, Integer rating) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setReview(review);
        booking.setRating(rating);
        bookingRepository.save(booking);
    }

    public void hideReview(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setHidden(true);
            bookingRepository.save(booking);
        }
    }

    public void unhideReview(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setHidden(false);
            bookingRepository.save(booking);
        }
    }
    
    /**
     * Lấy tất cả các đặt sân theo ID của chủ sân
     * @param ownerId ID của chủ sân
     * @return Danh sách đặt sân
     */
    public List<Booking> getBookingsByCourtOwnerId(Long ownerId) {
        return bookingRepository.findByCourt_CourtOwner_Id(ownerId);
    }
    
    /**
     * Lấy tất cả đặt sân theo ID của sân
     * @param courtId ID của sân
     * @return Danh sách đặt sân
     */
    public List<Booking> getBookingsByCourtId(Long courtId) {
        return bookingRepository.findByCourt_Id(courtId);
    }
}