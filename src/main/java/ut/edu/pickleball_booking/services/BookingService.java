package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ut.edu.pickleball_booking.dto.request.BookingRequestDto;
import ut.edu.pickleball_booking.entity.Booking;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.TimeSlot;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.repositories.BookingRepository;
import ut.edu.pickleball_booking.repositories.CourtRepository;
import ut.edu.pickleball_booking.repositories.TimeSlotRepository;
import ut.edu.pickleball_booking.repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.time.LocalDate;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

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

    public void saveBookingFromRequest(BookingRequestDto dto) {
        Court court = courtRepository.findById(dto.getCourtId())
            .orElseThrow(() -> new RuntimeException("Court not found"));
    
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
    
        TimeSlot timeSlot = timeSlotRepository.findById(dto.getTimeSlotId())
            .orElseThrow(() -> new RuntimeException("TimeSlot not found"));
    
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCourt(court);
        booking.setTimeSlot(timeSlot);
        booking.setPrice(dto.getPrice());
        booking.setBookingDate(dto.getBookingDate());
        booking.setHidden(false); // mặc định không ẩn
    
        bookingRepository.save(booking);
    }
    
}