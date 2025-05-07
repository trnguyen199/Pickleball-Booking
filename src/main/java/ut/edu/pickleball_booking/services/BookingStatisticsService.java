package ut.edu.pickleball_booking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.pickleball_booking.entity.BookingStatistics;
import ut.edu.pickleball_booking.repositories.BookingStatisticsRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookingStatisticsService {

    private final BookingStatisticsRepository bookingStatisticsRepository;

    @Autowired
    public BookingStatisticsService(BookingStatisticsRepository bookingStatisticsRepository) {
        this.bookingStatisticsRepository = bookingStatisticsRepository;
    }

    public void updateStatistics() {
        LocalDate today = LocalDate.now();
        BookingStatistics stats = bookingStatisticsRepository.findByDate(today)
                .orElse(new BookingStatistics());

        if (stats.getId() == null) {
            stats.setDate(today);
            stats.setBookings(0);
            stats.setRevenue(BigDecimal.ZERO);
        }

        stats.setBookings(stats.getBookings() + 1);
        stats.setRevenue(stats.getRevenue().add(BigDecimal.valueOf(100000))); // 100k mỗi lượt đặt
        bookingStatisticsRepository.save(stats);
    }

    public BigDecimal getDailyRevenue(LocalDate date) {
        return bookingStatisticsRepository.getDailyRevenue(date);
    }

    public BigDecimal getMonthlyRevenue(int year, int month) {
        return bookingStatisticsRepository.getMonthlyRevenue(year, month);
    }

    public BigDecimal getYearlyRevenue(int year) {
        return bookingStatisticsRepository.getYearlyRevenue(year);
    }

    public Integer getMonthlyBookings(int year, int month) {
        return bookingStatisticsRepository.getMonthlyBookings(year, month);
    }

    public BigDecimal getDailyRevenueByOwner(LocalDate date, Long ownerId) {
        return bookingStatisticsRepository.getDailyRevenueByOwner(date, ownerId);
    }

    public BigDecimal getMonthlyRevenueByOwner(int year, int month, Long ownerId) {
        return bookingStatisticsRepository.getMonthlyRevenueByOwner(year, month, ownerId);
    }

    public BigDecimal getYearlyRevenueByOwner(int year, Long ownerId) {
        return bookingStatisticsRepository.getYearlyRevenueByOwner(year, ownerId);
    }

    public Integer getMonthlyBookingsByOwner(int year, int month, Long ownerId) {
        return bookingStatisticsRepository.getMonthlyBookingsByOwner(year, month, ownerId);
    }
}