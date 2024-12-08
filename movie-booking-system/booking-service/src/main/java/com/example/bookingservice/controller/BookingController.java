package com.example.bookingservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

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
        logger.debug("Received request to book tickets: {}", bookingDTO);
        PaymentStrategy paymentStrategy = getPaymentStrategy(bookingDTO.getPaymentMethod());
        BookingFactory bookingFactory = getBookingFactory(bookingDTO.getBookingChannel());
        Booking booking = bookingService.bookTickets(bookingDTO.getUserId(), bookingDTO.getTicketCount(), bookingDTO.getShowTime(),
                bookingDTO.getTicketPrice(), paymentStrategy, bookingFactory);
        logger.info("Tickets booked successfully: {}", booking);
        return booking;
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        logger.debug("Received request to get booking by id: {}", id);
        Booking booking = bookingService.getBookingById(id);
        logger.info("Retrieved booking: {}", booking);
        return booking;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        logger.debug("Received request to get all bookings");
        List<Booking> bookings = bookingService.getAllBookings();
        logger.info("Retrieved {} bookings", bookings.size());
        return bookings;
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
        logger.debug("Received request to update booking with id: {}", id);
        Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        logger.info("Booking with id {} updated successfully: {}", id, updatedBooking);
        return updatedBooking;
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        logger.debug("Received request to delete booking with id: {}", id);
        bookingService.deleteBooking(id);
        logger.info("Booking with id {} deleted successfully", id);
    }

    private PaymentStrategy getPaymentStrategy(String paymentMethod) {
        logger.debug("Getting payment strategy for method: {}", paymentMethod);
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
        logger.debug("Getting booking factory for channel: {}", bookingChannel);
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
