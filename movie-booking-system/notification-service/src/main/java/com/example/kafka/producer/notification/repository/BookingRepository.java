package com.example.kafka.producer.notification.repository;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.kafka.producer.notification.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    //@Query("SELECT b FROM Booking b WHERE b.showTime BETWEEN :startTime AND :endTime")
	 @Query("SELECT b FROM Booking b")
    List<Booking> findUpcomingBookings(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    default List<Booking> findUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoHoursLater = now.plusHours(2);
        return findUpcomingBookings(now, twoHoursLater);
    }
}
