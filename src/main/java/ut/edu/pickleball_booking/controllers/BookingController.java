package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ut.edu.pickleball_booking.entity.TimeSlot;
import ut.edu.pickleball_booking.repositories.TimeSlotRepository;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookingController {

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public BookingController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @GetMapping("/danhsachsan/datsan/{courtId}")
    public String getTimeSlotsByCourt(@PathVariable Long courtId, Model model) {
        // Lấy danh sách TimeSlot theo courtId
        List<TimeSlot> timeSlots = timeSlotRepository.findByCourtId(courtId);
        model.addAttribute("timeSlots", timeSlots);
        model.addAttribute("courtId", courtId); // Truyền thêm courtId để hiển thị thông tin sân
        return "public/datsan";
    }
}