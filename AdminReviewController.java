package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ut.edu.pickleball_booking.entity.User;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.Booking;
import ut.edu.pickleball_booking.services.UserService;
import ut.edu.pickleball_booking.services.CourtService;
import ut.edu.pickleball_booking.services.BookingService;

import java.util.List;

@Controller
@RequestMapping("/admin/admin-reviews")
public class AdminReviewController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourtService courtService;
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String adminReviews(
            @RequestParam(value = "ownerId", required = false) Long ownerId,
            @RequestParam(value = "courtId", required = false) Long courtId,
            Model model
    ) {
        // Lấy danh sách chủ sân (role owner)
        List<User> owners = userService.getUsersByRole("ROLE_OWNER");
        model.addAttribute("owners", owners);

        // Lấy danh sách sân theo chủ sân đã chọn
        List<Court> courts = (ownerId != null) ? courtService.getCourtsByOwnerId(ownerId) : List.of();
        model.addAttribute("courts", courts);

        // Lấy danh sách đánh giá theo sân đã chọn
        List<Booking> reviews = (courtId != null) ? bookingService.getReviewsByCourtId(courtId) : List.of();
        model.addAttribute("reviews", reviews);

        // Truyền lại id đã chọn để giữ trạng thái select
        model.addAttribute("selectedOwnerId", ownerId);
        model.addAttribute("selectedCourtId", courtId);

        return "admin/admin-reviews";
    }
}
