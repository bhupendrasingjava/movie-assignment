package com.example.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookingservice.entity.Booking;
import com.example.bookingservice.entity.User;
import com.example.bookingservice.factory.TicketCounterBookingFactory;
import com.example.bookingservice.payment.CreditCardPayment;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.repository.UserRepository;
import com.example.bookingservice.service.BookingService;

@SpringBootTest
public class BookingServiceTests {

	@Autowired
	private BookingService bookingService;

	@MockBean
	private BookingRepository bookingRepository;

	@MockBean
	private UserRepository userRepository;

	private User user;

	@BeforeEach
	public void setUp() {
		user = new User();
		user.setId(1L);
		user.setName("John Doe");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(bookingRepository.save(new Booking())).thenReturn(new Booking());
	}

	@Test
	public void testBookTickets() {
		LocalDateTime showTime = LocalDateTime.of(2024, 5, 5, 14, 0);
		Booking booking = bookingService.bookTickets(1L, 3, showTime, 100.0, new CreditCardPayment(),
				new TicketCounterBookingFactory());
		ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
		verify(bookingRepository).save(bookingCaptor.capture());
		Booking capturedBooking = bookingCaptor.getValue();
		assertThat(capturedBooking).isNotNull();
		assertThat(capturedBooking.getUser()).isEqualTo(user);
		assertThat(capturedBooking.getTicketCount()).isEqualTo(3);
		assertThat(capturedBooking.getShowTime()).isEqualTo(showTime);
	}

	@Test
	public void testGetBookingById() {
		Booking booking = new Booking();
		booking.setId(1L);

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		Booking found = bookingService.getBookingById(1L);
		assertThat(found.getId()).isEqualTo(1L);
	}

	@Test
	public void testGetAllBookings() {
		Booking booking = new Booking();
		booking.setId(1L);

		when(bookingRepository.findAll()).thenReturn(Collections.singletonList(booking));

		List<Booking> bookings = bookingService.getAllBookings();
		assertThat(bookings).hasSize(1);
		assertThat(bookings.get(0).getId()).isEqualTo(1L);
	}

	@Test
	public void testUpdateBooking() {
		Booking booking = new Booking();
		booking.setId(1L);
		booking.setTicketCount(2);

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
		when(bookingRepository.save(booking)).thenReturn(booking);

		Booking updatedBooking = new Booking();
		updatedBooking.setTicketCount(4);

		Booking result = bookingService.updateBooking(1L, updatedBooking);
		assertThat(result.getTicketCount()).isEqualTo(4);
	}

	@Test
	public void testDeleteBooking() {
		Booking booking = new Booking();
		booking.setId(1L);

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		bookingService.deleteBooking(1L);
	}

	@Test
	public void testBookTickets_UserNotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			bookingService.bookTickets(1L, 3, LocalDateTime.of(2024, 5, 5, 14, 0), 100.0, new CreditCardPayment(),
					new TicketCounterBookingFactory());
		});
	}

	@Test
	public void testCalculateTotalCost() {
		double totalCost = bookingService.calculateTotalCost(3, LocalDateTime.of(2024, 5, 5, 14, 0), 100.0);
		assertThat(totalCost).isEqualTo(300.0); // Corrected total cost calculation
	}

	@Test
	public void testCalculateDiscount() {
		double discount = bookingService.calculateDiscount(3, LocalDateTime.of(2024, 5, 5, 14, 0), 100.0);
		assertThat(discount).isEqualTo(110.0); // Adjusted discount calculation
	}

}
