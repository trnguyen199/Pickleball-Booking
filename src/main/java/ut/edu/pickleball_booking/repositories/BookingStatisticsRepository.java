package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.pickleball_booking.entity.BookingStatistics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface BookingStatisticsRepository extends JpaRepository<BookingStatistics, Long> {

    Optional<BookingStatistics> findByDate(LocalDate date);

    @Query("SELECT SUM(revenue) FROM BookingStatistics WHERE date = :date")
    BigDecimal getDailyRevenue(@Param("date") LocalDate date);

    @Query("SELECT SUM(revenue) FROM BookingStatistics WHERE YEAR(date) = :year AND MONTH(date) = :month")
    BigDecimal getMonthlyRevenue(@Param("year") int year, @Param("month") int month);

    @Query("SELECT SUM(revenue) FROM BookingStatistics WHERE YEAR(date) = :year")
    BigDecimal getYearlyRevenue(@Param("year") int year);

    @Query("SELECT SUM(bookings) FROM BookingStatistics WHERE YEAR(date) = :year AND MONTH(date) = :month")
    Integer getMonthlyBookings(@Param("year") int year, @Param("month") int month);
}