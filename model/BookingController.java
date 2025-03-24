package com.pbs.controller;

import com.pbs.dto.BookingDto;
import com.pbs.model.Booking;
import com.pbs.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or @bookingSecurity.isBookingUser(#id)")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.isCurrentUser(#userId)")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    @GetMapping("/court/{courtId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') and @courtSecurity.isCourtManager(#courtId)")
    public ResponseEntity<List<Booking>> getBookingsByCourtId(@PathVariable UUID courtId) {
        return ResponseEntity.ok(bookingService.getBookingsByCourtId(courtId));
    }

    @GetMapping("/court/{courtId}/date-range")
    public ResponseEntity<List<Booking>> getBookingsByCourtIdAndDateRange(
            @PathVariable UUID courtId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.getBookingsByCourtIdAndDateRange(courtId, startDate, endDate));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable Booking.BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        Booking createdBooking = bookingService.createBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @bookingSecurity.isBookingUser(#id)")
    public ResponseEntity<Booking> updateBooking(@PathVariable UUID id, @Valid @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or @bookingSecurity.isBookingUser(#id)")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable UUID id, @RequestParam Booking.BookingStatus status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @bookingSecurity.isBookingUser(#id)")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkTimeSlotAvailability(
            @RequestParam UUID courtId,
            @RequestParam int courtNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return ResponseEntity.ok(bookingService.isTimeSlotAvailable(courtId, courtNumber, startTime, endTime));
    }
}
