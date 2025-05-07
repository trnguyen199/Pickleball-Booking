package ut.edu.pickleball_booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ut.edu.pickleball_booking.entity.RevenueStatistics;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface RevenueStatisticsRepository extends JpaRepository<RevenueStatistics, Long> {

    @Query("SELECT SUM(totalRevenue) FROM RevenueStatistics WHERE DATE(createdAt) = :date")
    BigDecimal getRevenueByDate(@Param("date") LocalDate date);

    @Query("SELECT SUM(totalRevenue) FROM RevenueStatistics WHERE YEAR(createdAt) = :year AND MONTH(createdAt) = :month")
    BigDecimal getRevenueByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT SUM(totalRevenue) FROM RevenueStatistics WHERE YEAR(createdAt) = :year")
    BigDecimal getRevenueByYear(@Param("year") int year);

    @Query("SELECT COUNT(*) FROM RevenueStatistics WHERE YEAR(createdAt) = :year AND MONTH(createdAt) = :month")
    Integer getRegistrationsByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT SUM(totalBookings) FROM RevenueStatistics WHERE YEAR(createdAt) = :year AND MONTH(createdAt) = :month")
    Integer getTotalBookingsByMonth(@Param("year") int year, @Param("month") int month);
}