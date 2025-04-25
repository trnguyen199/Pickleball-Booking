package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ut.edu.pickleball_booking.entity.Court;
import ut.edu.pickleball_booking.entity.TimeSlot;
import ut.edu.pickleball_booking.repositories.CourtRepository;
import ut.edu.pickleball_booking.repositories.TimeSlotRepository;

import java.util.List;

@Controller
public class TimeSlotController {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private CourtRepository courtRepository;

    @GetMapping("/manage-timeslots")
    public String showManageTimeSlotsPage(Model model) {
        System.out.println(">> Entering showManageTimeSlotsPage");
        List<Court> courts = courtRepository.findAll();
        if (courts.isEmpty()) {
            Court c = new Court();
            c.setName("Sân A");
            courtRepository.save(c);  // Lưu vào DB
            courts = courtRepository.findAll(); // Đọc lại danh sách
        }
        System.out.println(">> Courts loaded (after findAll): " + courts.size());
        model.addAttribute("courts", courts);
        return "master/manage-timeslots";
    }
        
    // Endpoint nhận danh sách khung giờ từ form thông qua request parameters
    @PostMapping("/save")
    public ResponseEntity<?> saveTimeSlots(@RequestParam List<String> timeSlots) {
        try {
            // Với mỗi chuỗi gửi lên, định dạng: "startTime-endTime-courtId-price"
            for (String timeSlotStr : timeSlots) {
                String[] parts = timeSlotStr.split("-");
                if (parts.length != 4) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Định dạng thời gian không hợp lệ: " + timeSlotStr);
                }

                String startTime = parts[0];
                String endTime = parts[1];
                Long courtId = Long.parseLong(parts[2]);
                int price = Integer.parseInt(parts[3]); // ✅ Parse giá

                // Tìm sân theo ID
                Court court = courtRepository.findById(courtId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sân với ID: " + courtId));

                // Tạo đối tượng TimeSlot
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setStartTime(startTime);
                timeSlot.setEndTime(endTime);
                timeSlot.setCourt(court);
                timeSlot.setPrice(price); // ✅ Gán giá

                timeSlotRepository.save(timeSlot);
            }
            return ResponseEntity.ok("Khung giờ đã được lưu thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lưu khung giờ: " + e.getMessage());
        }
    }

}