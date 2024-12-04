package com.example.bookingservice.entity;

import java.time.LocalDateTime;

public interface BookingInterface {
    void setBookingDetails(Long userId, int ticketCount, LocalDateTime showTime, double ticketPrice, double discount, double totalAmount);
}
