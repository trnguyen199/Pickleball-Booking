package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.pickleball_booking.dto.request.BookingRequestDto;
import ut.edu.pickleball_booking.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingApiController {

    @PostMapping("/save")
    public ResponseEntity<?> saveBookings(@RequestBody List<BookingRequestDto> bookingRequests) {
        // xử lý list bookingRequests
        return ResponseEntity.ok("Saved successfully");
    }
}

// package ut.edu.pickleball_booking.controllers;

// import ut.edu.pickleball_booking.dto.request.BookingRequestDto;
// import ut.edu.pickleball_booking.services.BookingService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/bookings")
// public class BookingApiController {

//     @Autowired
//     private BookingService bookingService;

//     @PostMapping("/save")
//     public void saveBookings(@RequestBody List<BookingRequestDto> bookingRequests) {
//         bookingService.saveBookings(bookingRequests);
//     }
// }
