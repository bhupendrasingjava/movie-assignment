package com.example.bookingservice.factory;

import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.PaytmBooking;
import com.example.bookingservice.factory.BookingFactory;

public class PaytmBookingFactory implements BookingFactory {
	@Override
	public Booking createBooking() {
		return new PaytmBooking();
	}
}