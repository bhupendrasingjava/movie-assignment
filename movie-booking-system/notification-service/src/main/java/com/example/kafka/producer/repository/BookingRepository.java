package com.example.kafka.producer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.kafka.producer.model.booking.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.showTime BETWEEN :startTime AND :endTime")
    List<Booking> findUpcomingBookings(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    default List<Booking> findUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoHoursLater = now.plusHours(2);
        return findUpcomingBookings(now, twoHoursLater);
    }
}
