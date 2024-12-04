package com.example.bookingservice.entity;

import java.time.LocalDateTime;

import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.BookingInterface;

import jakarta.persistence.Entity;

@Entity
public class TicketCounterBooking extends Booking implements BookingInterface {
	@Override
	public void setBookingDetails(Long userId, int ticketCount, LocalDateTime showTime, double ticketPrice,
			double discount, double totalAmount) {
		this.setUserId(userId);
		this.setTicketCount(ticketCount);
		this.setShowTime(showTime);
		this.setTotalCost(ticketCount * ticketPrice);
		this.setBookingDate(LocalDateTime.now());
		this.setTotalAmount(totalAmount);
		this.setNoOfSeats(ticketCount);
		this.setDiscountApplied(discount);
	}
}