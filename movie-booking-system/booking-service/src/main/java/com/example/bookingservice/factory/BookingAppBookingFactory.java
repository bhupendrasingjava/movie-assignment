package com.example.bookingservice.factory;

import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.BookingAppBooking;
import com.example.bookingservice.factory.BookingFactory;

public class BookingAppBookingFactory implements BookingFactory {
	@Override
	public Booking createBooking() {
		return new BookingAppBooking();
	}
}
