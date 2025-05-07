package ut.edu.pickleball_booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import ut.edu.pickleball_booking.entity.RevenueStatistics;
import ut.edu.pickleball_booking.repositories.RevenueStatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
public class RevenueStatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(RevenueStatisticsController.class);

    @Autowired
    private RevenueStatisticsRepository revenueStatisticsRepository;

    @GetMapping("/admin/revenue-statistics")
    public List<RevenueStatistics> getRevenueStatistics() {
        return revenueStatisticsRepository.findAll();
    }

    @GetMapping("/admin/statistics-data")
    @ResponseBody
    public Map<String, Object> getAdminStatisticsData() {
        LocalDate today = LocalDate.now();

        // Tính doanh thu từ các gói đăng ký
        BigDecimal dailyRevenue = revenueStatisticsRepository.getRevenueByDate(today);
        BigDecimal monthlyRevenue = revenueStatisticsRepository.getRevenueByMonth(today.getYear(), today.getMonthValue());
        BigDecimal yearlyRevenue = revenueStatisticsRepository.getRevenueByYear(today.getYear());

        // Log dữ liệu để kiểm tra
        logger.info("Daily Revenue: {}", dailyRevenue);
        logger.info("Monthly Revenue: {}", monthlyRevenue);
        logger.info("Yearly Revenue: {}", yearlyRevenue);

        Map<String, Object> data = new HashMap<>();
        data.put("dailyRevenue", dailyRevenue != null ? dailyRevenue : BigDecimal.ZERO);
        data.put("monthlyRevenue", monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);
        data.put("yearlyRevenue", yearlyRevenue != null ? yearlyRevenue : BigDecimal.ZERO);

        return data;
    }

    @GetMapping("/admin/statistics-chart-data")
    @ResponseBody
    public Map<String, Object> getAdminStatisticsChartData() {
        List<String> months = Arrays.asList("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12");
        List<BigDecimal> monthlyRevenues = new ArrayList<>();
        List<Integer> monthlyRegistrations = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            BigDecimal revenue = revenueStatisticsRepository.getRevenueByMonth(2025, month);
            Integer registrations = revenueStatisticsRepository.getTotalBookingsByMonth(2025, month);

            monthlyRevenues.add(revenue != null ? revenue : BigDecimal.ZERO);
            monthlyRegistrations.add(registrations != null ? registrations : 0);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("months", months);
        data.put("revenues", monthlyRevenues);
        data.put("registrations", monthlyRegistrations);

        return data;
    }
}