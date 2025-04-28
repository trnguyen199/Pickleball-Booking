package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ut.edu.pickleball_booking.entity.TimeSlot;
import ut.edu.pickleball_booking.repositories.TimeSlotRepository;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import ut.edu.pickleball_booking.services.BookingService;
import ut.edu.pickleball_booking.entity.Booking;

@Controller
public class BookingController {

    private final TimeSlotRepository timeSlotRepository;
    private final BookingService bookingService;

    @Autowired
    public BookingController(TimeSlotRepository timeSlotRepository, BookingService bookingService) {

        this.timeSlotRepository = timeSlotRepository;
        this.bookingService = bookingService;
    }

    @GetMapping("/danhsachsan/datsan/{courtId}")
    public String getTimeSlotsByCourt(@PathVariable Long courtId, Model model) {
        // Lấy danh sách TimeSlot theo courtId
        List<TimeSlot> timeSlots = timeSlotRepository.findByCourtId(courtId);
        model.addAttribute("timeSlots", timeSlots);
        model.addAttribute("courtId", courtId); // Truyền thêm courtId để hiển thị thông tin sân

        // Lấy danh sách đánh giá
        List<Booking> reviews = bookingService.getReviewsByCourtIdNotHidden(courtId);
        model.addAttribute("reviews", reviews);

        // Tính trung bình sao
        double averageRating = reviews.stream()
                .filter(r -> r.getRating() != null)
                .mapToInt(Booking::getRating)
                .average()
                .orElse(0.0);
        double roundedRating = Math.round(averageRating * 2) / 2.0;
        model.addAttribute("averageRating", roundedRating);

        return "public/datsan";
    }
}