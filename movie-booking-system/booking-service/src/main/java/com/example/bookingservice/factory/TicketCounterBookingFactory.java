package com.example.bookingservice.factory;

import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.TicketCounterBooking;
import com.example.bookingservice.factory.BookingFactory;

public class TicketCounterBookingFactory implements BookingFactory {
    @Override
    public Booking createBooking() {
        return new TicketCounterBooking();
    }
}