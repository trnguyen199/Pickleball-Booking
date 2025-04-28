package ut.edu.pickleball_booking.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

import ut.edu.pickleball_booking.entity.*;
import ut.edu.pickleball_booking.repositories.CourtRepository;
import ut.edu.pickleball_booking.repositories.TimeSlotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import ut.edu.pickleball_booking.services.UserService;
import ut.edu.pickleball_booking.services.CourtService;
import ut.edu.pickleball_booking.services.BookingService;
import ut.edu.pickleball_booking.services.BookingStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class DanhChoChuSanController {
    private final UserService userService;
    private final CourtService courtService;
    private final CourtRepository courtRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final BookingService bookingService;
    private final BookingStatisticsService bookingStatisticsService;
    private static final Logger logger = LoggerFactory.getLogger(DanhChoChuSanController.class);


    @Autowired
    public DanhChoChuSanController(UserService userService, CourtService courtService,CourtRepository courtRepository, TimeSlotRepository timeSlotRepository, BookingService bookingService, BookingStatisticsService bookingStatisticsService) {
        this.userService = userService;
        this.courtService = courtService;
        this.courtRepository = courtRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.bookingService = bookingService;
        this.bookingStatisticsService = bookingStatisticsService;

    }
    
            


    @GetMapping("/danhchochusan")
    public String getDanhSachSan(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login"; 
        }
    
        // Lấy username của người dùng đã đăng nhập
        String username = principal.getName();
        System.out.println("Logged-in username: " + username);
    
        // Lấy danh sách vai trò của người dùng
        List<Role> roles = userService.getRolesByUsername(username);
        if (roles == null || roles.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy vai trò nào cho người dùng.");
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu không có vai trò
        }
    
        // Kiểm tra vai trò của người dùng
        boolean isCustomer = roles.stream().anyMatch(role -> role.getName().equals("ROLE_CUSTOMER"));
        boolean isOwner = roles.stream().anyMatch(role -> role.getName().equals("ROLE_OWNER"));
    
        if (isCustomer) {
            System.out.println("User is a customer. Redirecting to registration page.");
            return "public/dangkychusan"; // Chuyển hướng đến trang đăng ký làm chủ sân
        } else if (isOwner) {
            System.out.println("User is an owner. Accessing owner page.");
            model.addAttribute("roles", roles); // Thêm danh sách vai trò vào model
            try {
                User user = userService.findByUsername(username);
                model.addAttribute("user", user); // Thêm thông tin người dùng vào model
            } catch (Exception e) {
                System.out.println("Error fetching user details: " + e.getMessage());
                model.addAttribute("error", "Không tìm thấy thông tin người dùng.");
            }
            return "manage/manage-statistics"; // Trả về template cho chủ sân
        }
    
        // Nếu không thuộc vai trò nào, yêu cầu đăng nhập
        System.out.println("User has no valid role. Redirecting to login page.");
        return "redirect:/login";
    }

    @PostMapping("/danhchochusan/thanhtoan")
    public String handlePayment(@RequestParam("plan") String plan, Model model) {
        System.out.println("Selected plan: " + plan);
    
        // Thêm dữ liệu vào model để hiển thị trên trang thanhtoan.html
        model.addAttribute("selectedPlan", plan);
    
        // Chuyển hướng đến trang thanh toán
        return "master/thanhtoan";

    }

    @GetMapping("/danhchochusan/update-info")
    public String updateInfo(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        model.addAttribute("user", userService.findByUsername(username));
        model.addAttribute("roles", userService.getRolesByUsername(username));
        return "manage/update-info"; 
    }

    @GetMapping("/danhchochusan/manage-courts")
    public String manageCourts(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        List<Court> courts = courtService.getCourtsByOwnerId(userId);
        model.addAttribute("courts", courts);
        model.addAttribute("userId", userId);

        return "manage/manage-courts";
    }

    @GetMapping("/danhchochusan/manage-timeslots")
    public String manageTimeslots(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; 
        }

        List<Court> courts = courtService.getCourtsByOwnerId(userId);
        model.addAttribute("courts", courts); // Thêm danh sách sân vào model
        return "manage/manage-timeslots"; // Trả về template cho trang quản lý khung giờ
    }

    @PostMapping("/danhchochusan/manage-timeslots/save")
    public String saveTimeSlots(@RequestParam List<String> timeSlots, Model model) {
        try {
            for (String timeSlotStr : timeSlots) {
                String[] parts = timeSlotStr.split("-");
                if (parts.length != 4) {
                    model.addAttribute("error", "Định dạng thời gian không hợp lệ: " + timeSlotStr);
                    return "redirect:/danhchochusan/manage-timeslots";
                }

                String startTime = parts[0];
                String endTime = parts[1];
                Long courtId = Long.parseLong(parts[2]);
                int price = Integer.parseInt(parts[3]);

                Court court = courtRepository.findById(courtId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sân với ID: " + courtId));

                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setStartTime(startTime);
                timeSlot.setEndTime(endTime);
                timeSlot.setCourt(court);
                timeSlot.setPrice(price);

                timeSlotRepository.save(timeSlot);
            }
            model.addAttribute("success", "Khung giờ đã được lưu thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lưu khung giờ: " + e.getMessage());
        }
        return "redirect:/danhchochusan/manage-timeslots";
    }

        @GetMapping("/danhchochusan/manage-timeslots/get")
        @ResponseBody
        public List<TimeSlot> getTimeSlotsByCourt(@RequestParam Long courtId) {
            return timeSlotRepository.findByCourtId(courtId); 
        }




    @GetMapping("/danhchochusan/bookings")
    public String bookings() {
        return "manage/manage-booking"; // Tên template cho trang quản lý đặt sân
    }
    @GetMapping("/danhchochusan/statistics")
    public String statisticsPage() {
        return "manage/manage-statistics"; // Trả về template cho trang thống kê
    }
    @PostMapping("/danhchochusan/update-statistics")
    @ResponseBody
    public String updateStatistics() {
        bookingStatisticsService.updateStatistics();
        return "Statistics updated successfully";
    }

    @GetMapping("/danhchochusan/statistics-data")
    @ResponseBody
    public Map<String, Object> getStatisticsData() {
        LocalDate today = LocalDate.now();

        BigDecimal dailyRevenue = bookingStatisticsService.getDailyRevenue(today);
        BigDecimal monthlyRevenue = bookingStatisticsService.getMonthlyRevenue(today.getYear(), today.getMonthValue());
        BigDecimal yearlyRevenue = bookingStatisticsService.getYearlyRevenue(today.getYear());

        // Log dữ liệu để kiểm tra
        System.out.println("Daily Revenue: " + dailyRevenue);
        System.out.println("Monthly Revenue: " + monthlyRevenue);
        System.out.println("Yearly Revenue: " + yearlyRevenue);

        Map<String, Object> data = new HashMap<>();
        data.put("dailyRevenue", dailyRevenue != null ? dailyRevenue : BigDecimal.ZERO);
        data.put("monthlyRevenue", monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);
        data.put("yearlyRevenue", yearlyRevenue != null ? yearlyRevenue : BigDecimal.ZERO);

        return data;
    }

    @GetMapping("/danhchochusan/statistics-chart-data")
    @ResponseBody
    public Map<String, Object> getStatisticsChartData() {
        // Dữ liệu mẫu cho 12 tháng
        List<String> months = Arrays.asList("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12");

        // Lấy dữ liệu doanh thu và số lượt đặt sân từ bảng booking_statistics
        List<BigDecimal> revenue = new ArrayList<>();
        List<Integer> bookings = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            BigDecimal monthlyRevenue = bookingStatisticsService.getMonthlyRevenue(2025, month);
            Integer monthlyBookings = bookingStatisticsService.getMonthlyBookings(2025, month);

            // Log dữ liệu để kiểm tra
            logger.info("Tháng {}: Doanh thu = {}, Số lượt đặt = {}", month, monthlyRevenue, monthlyBookings);

            // Đảm bảo doanh thu không bị chia nhỏ
            BigDecimal normalizedRevenue = (monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);

            revenue.add(normalizedRevenue);
            bookings.add(monthlyBookings != null ? monthlyBookings : 0);
        }

        // Trả về dữ liệu dưới dạng JSON
        Map<String, Object> data = new HashMap<>();
        data.put("months", months);
        data.put("revenue", revenue);
        data.put("bookings", bookings);

        return data;
    }
    @GetMapping("/danhchochusan/reviews")
    public String reviews(@RequestParam(value = "courtId", required = false) Long courtId, HttpSession session, Model model) {
        Long ownerId = (Long) session.getAttribute("userId");
        List<Court> courts = courtService.getCourtsByOwnerId(ownerId);
        model.addAttribute("courts", courts);

        if (courtId == null && !courts.isEmpty()) {
            courtId = courts.get(0).getId();
        }
        model.addAttribute("selectedCourtId", courtId);

        // Lấy tất cả review (không lọc hidden)
        List<Booking> reviews = bookingService.getReviewsByCourtId(courtId);
        model.addAttribute("reviews", reviews);

        return "manage/manage-reviews";
    }
    @PostMapping("/danhchochusan/reviews/hide")
    public String hideReview(@RequestParam("bookingId") Long bookingId,
                             @RequestParam("courtId") Long courtId) {
        bookingService.hideReview(bookingId);
        return "redirect:/danhchochusan/reviews?courtId=" + courtId;
    }

    @PostMapping("/danhchochusan/reviews/unhide")
    public String unhideReview(@RequestParam("bookingId") Long bookingId,
                               @RequestParam("courtId") Long courtId) {
        bookingService.unhideReview(bookingId);
        return "redirect:/danhchochusan/reviews?courtId=" + courtId;
    }
    @GetMapping("/danhchochusan/withdrawals")
    public String withdrawals() {
        return "manage/manage-withdrawals"; // Tên template cho trang rút tiền
    }
}
