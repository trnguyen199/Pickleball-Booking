package ut.edu.pickleball_booking.controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ut.edu.pickleball_booking.entity.Booking;
import ut.edu.pickleball_booking.services.BookingService;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String showAllBookings(Model model) {
        model.addText("bookings", bookingService.getAllBookings());
        return "booking/list";
    }

    @GetMapping("/new")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "booking/form";
    }

    @PostMapping
    public String saveBooking(@ModelAttribute Booking booking) {
        bookingService.saveBooking(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings";
    }
}

