package com.pbs.service;

import com.pbs.dto.BookingDto;
import com.pbs.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingService {
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(UUID id);
    List<Booking> getBookingsByUserId(UUID userId);
    List<Booking> getBookingsByCourtId(UUID courtId);
    List<Booking> getBookingsByCourtIdAndDateRange(UUID courtId, LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> getBookingsByStatus(Booking.BookingStatus status);
    Booking createBooking(BookingDto bookingDto);
    Booking updateBooking(UUID id, BookingDto bookingDto);
    Booking updateBookingStatus(UUID id, Booking.BookingStatus status);
    void deleteBooking(UUID id);
    boolean isTimeSlotAvailable(UUID courtId, int courtNumber, LocalDateTime startTime, LocalDateTime endTime);
}
