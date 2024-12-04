package com.example.bookingservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.User;
import com.example.bookingservice.factory.BookingFactory;
import com.example.bookingservice.payment.PaymentContext;
import com.example.bookingservice.payment.PaymentStrategy;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.UserRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentContext paymentContext;

	private final Lock bookingLock = new ReentrantLock();

	public Booking bookTickets(Long userId, int ticketCount, LocalDateTime showTime, double ticketPrice,
			PaymentStrategy paymentStrategy, BookingFactory bookingFactory) {
		bookingLock.lock();
		Booking booking = null;
		try {
			User user = userRepository.findById(userId).orElse(null);
			if (user == null) {
				throw new RuntimeException("User not found");
			}
			double totalCost = calculateTotalCost(ticketCount, showTime, ticketPrice);
			double discount = calculateDiscount(ticketCount, showTime, ticketPrice);
			double totalAmount = (ticketCount * ticketPrice) - discount;

			// Use the PaymentStrategy for processing the payment
			boolean paymentSuccessful = paymentStrategy.processPayment(userId, totalAmount);
			if (!paymentSuccessful) {
				throw new RuntimeException("Payment failed");
			}
			booking = bookingFactory.createBooking();
			booking.setBookingDetails(userId, ticketCount, showTime, ticketPrice, discount, totalAmount);
			booking = bookingRepository.save(booking);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bookingLock.unlock();
		}
		return booking;
	}


	public Booking updateBooking(Long id, Booking bookingDetails) {
		Booking booking = bookingRepository.findById(id).orElse(null);
		if (booking != null) {
			booking.setTicketCount(bookingDetails.getTicketCount());
			booking.setShowTime(bookingDetails.getShowTime());
			booking.setTotalCost(bookingDetails.getTotalCost());
			booking.setTotalAmount(bookingDetails.getTotalAmount());
			booking.setNoOfSeats(bookingDetails.getNoOfSeats());
			booking.setDiscountApplied(bookingDetails.getDiscountApplied());
			return bookingRepository.save(booking);
		}
		return null;
	}

	public Booking getBookingById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}

	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	public void deleteBooking(Long id) {
		bookingRepository.deleteById(id);
	}

	public double calculateTotalCost(int ticketCount, LocalDateTime showTime, double ticketPrice) {
		double discount = calculateDiscount(ticketCount, showTime, ticketPrice);
		return (ticketCount * ticketPrice) - discount;
	}

	public double calculateDiscount(int ticketCount, LocalDateTime showTime, double ticketPrice) {
		double discount = 0.0;

		// 50% discount on the third ticket
		if (ticketCount >= 3) {
			discount += ticketPrice * 0.5;
		}

		// 20% discount on afternoon shows
		LocalTime time = showTime.toLocalTime();
		if (time.isAfter(LocalTime.NOON) && time.isBefore(LocalTime.of(17, 0))) {
			discount += ticketCount * ticketPrice * 0.2;
		}

		return discount;
	}

}
