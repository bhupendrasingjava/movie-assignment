package com.example.bookingservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

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
            logger.debug("Attempting to book tickets: userId={}, ticketCount={}, showTime={}, ticketPrice={}", userId, ticketCount, showTime, ticketPrice);
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                logger.error("User not found: userId={}", userId);
                throw new RuntimeException("User not found");
            }
            double totalCost = calculateTotalCost(ticketCount, showTime, ticketPrice);
            double discount = calculateDiscount(ticketCount, showTime, ticketPrice);
            double totalAmount = (ticketCount * ticketPrice) - discount;
            
            logger.debug("Calculated total cost: {}, discount: {}, total amount: {}", totalCost, discount, totalAmount);

            boolean paymentSuccessful = paymentStrategy.processPayment(userId, totalAmount);
            if (!paymentSuccessful) {
                logger.error("Payment failed for userId={}", userId);
                throw new RuntimeException("Payment failed");
            }

            booking = bookingFactory.createBooking();
            booking.setBookingDetails(userId, ticketCount, showTime, ticketPrice, discount, totalAmount);
            booking = bookingRepository.save(booking);
            logger.info("Booking successful: {}", booking);
            
        } catch (Exception e) {
            logger.error("Error occurred while booking tickets", e);
        } finally {
            bookingLock.unlock();
        }
        return booking;
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        logger.debug("Received request to update booking with id: {}", id);
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setTicketCount(bookingDetails.getTicketCount());
            booking.setShowTime(bookingDetails.getShowTime());
            booking.setTotalCost(bookingDetails.getTotalCost());
            booking.setTotalAmount(bookingDetails.getTotalAmount());
            booking.setNoOfSeats(bookingDetails.getNoOfSeats());
            booking.setDiscountApplied(bookingDetails.getDiscountApplied());
            Booking updatedBooking = bookingRepository.save(booking);
            logger.info("Booking with id {} updated successfully: {}", id, updatedBooking);
            return updatedBooking;
        } else {
            logger.error("Booking not found: id={}", id);
            return null;
        }
    }

    public Booking getBookingById(Long id) {
        logger.debug("Received request to get booking by id: {}", id);
        Booking booking = bookingRepository.findById(id).orElse(null);
        logger.info("Retrieved booking: {}", booking);
        return booking;
    }

    public List<Booking> getAllBookings() {
        logger.debug("Received request to get all bookings");
        List<Booking> bookings = bookingRepository.findAll();
        logger.info("Retrieved {} bookings", bookings.size());
        return bookings;
    }

    public void deleteBooking(Long id) {
        logger.debug("Received request to delete booking with id: {}", id);
        bookingRepository.deleteById(id);
        logger.info("Booking with id {} deleted successfully", id);
    }

    public double calculateTotalCost(int ticketCount, LocalDateTime showTime, double ticketPrice) {
        double discount = calculateDiscount(ticketCount, showTime, ticketPrice);
        double totalCost = (ticketCount * ticketPrice) - discount;
        logger.debug("Calculated total cost: ticketCount={}, showTime={}, ticketPrice={}, discount={}, totalCost={}", ticketCount, showTime, ticketPrice, discount, totalCost);
        return totalCost;
    }

    public double calculateDiscount(int ticketCount, LocalDateTime showTime, double ticketPrice) {
        double discount = 0.0;

        if (ticketCount >= 3) {
            discount += ticketPrice * 0.5;
        }

        LocalTime time = showTime.toLocalTime();
        if (time.isAfter(LocalTime.NOON) && time.isBefore(LocalTime.of(17, 0))) {
            discount += ticketCount * ticketPrice * 0.2;
        }
        logger.debug("Calculated discount: ticketCount={}, showTime={}, ticketPrice={}, discount={}", ticketCount, showTime, ticketPrice, discount);
        return discount;
    }

}
