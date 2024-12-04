package com.example.bookingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingservice.dto.BookingDTO;
import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.exception.InvalidPaymentMethodException;
import com.example.bookingservice.factory.BookingFactory;
import com.example.bookingservice.payment.CreditCardPayment;
import com.example.bookingservice.payment.NetbankingPayment;
import com.example.bookingservice.payment.PaymentStrategy;
import com.example.bookingservice.payment.UPIPayment;
import com.example.bookingservice.payment.WalletPayment;
import com.example.bookingservice.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private UPIPayment upiPayment;
	@Autowired
	private NetbankingPayment netbankingPayment;
	@Autowired
	private CreditCardPayment creditCardPayment;
	@Autowired
	private WalletPayment walletPayment;
	@Autowired
	private BookingFactory ticketCounterBookingFactory;

	@Autowired
	private BookingFactory paytmBookingFactory;

	@Autowired
	private BookingFactory bookingAppBookingFactory;


	@PostMapping("/bookTickets")
	public Booking bookTickets(@RequestBody BookingDTO bookingDTO) {
		PaymentStrategy paymentStrategy = getPaymentStrategy(bookingDTO.getPaymentMethod());
		BookingFactory bookingFactory = getBookingFactory(bookingDTO.getBookingChannel());
		return bookingService.bookTickets(bookingDTO.getUserId(), bookingDTO.getTicketCount(), bookingDTO.getShowTime(),
				bookingDTO.getTicketPrice(), paymentStrategy, bookingFactory);
	}

	@GetMapping("/{id}")
	public Booking getBookingById(@PathVariable Long id) {
		return bookingService.getBookingById(id);
	}

	@GetMapping
	public List<Booking> getAllBookings() {
		return bookingService.getAllBookings();
	}

	@PutMapping("/{id}")
	public Booking updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
		return bookingService.updateBooking(id, bookingDetails);
	}

	@DeleteMapping("/{id}")
	public void deleteBooking(@PathVariable Long id) {
		bookingService.deleteBooking(id);
	}

	private PaymentStrategy getPaymentStrategy(String paymentMethod) {
		switch (paymentMethod.toLowerCase()) {
		case "upi":
			return upiPayment;
		case "netbanking":
			return netbankingPayment;
		case "creditcard":
			return creditCardPayment;
		case "wallet":
			return walletPayment;
		default:
			throw new InvalidPaymentMethodException("Invalid payment method: " + paymentMethod);
		}
	}

	private BookingFactory getBookingFactory(String bookingChannel) {
		switch (bookingChannel) {
		case "PaytmBookingChannel":
			return this.paytmBookingFactory;
		case "BookingAppBookingChannel":
			return this.bookingAppBookingFactory;
		default:
			return this.ticketCounterBookingFactory;
		}
	}
}
