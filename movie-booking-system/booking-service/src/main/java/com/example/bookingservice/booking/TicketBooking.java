package com.example.bookingservice.booking;

import java.time.LocalTime;

import com.example.bookingservice.entity.Booking;

public interface TicketBooking {
    Booking setBookingDetails(Long userId, int ticketCount, LocalTime showTime, double ticketPrice, double discount, double totalAmount);
    // Other necessary methods
}
