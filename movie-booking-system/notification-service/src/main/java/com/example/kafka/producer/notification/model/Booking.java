package com.example.kafka.producer.notification.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "booking_type", discriminatorType = DiscriminatorType.STRING)
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_id")

	private Long userId;
	@Column(nullable = false) // Added movieName column private String movieName;
	private String movieName;
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	private LocalDateTime bookingDate;
	private int ticketCount;
	private LocalDateTime showTime;
	private double totalCost;
	private double totalAmount;
	private int noOfSeats;
	private double discountApplied;

	public void setBookingDetails(Long userId, int ticketCount, LocalDateTime showTime, double ticketPrice,
			double discount, double totalAmount) {
		this.userId = userId;
		this.ticketCount = ticketCount;
		this.showTime = showTime;
		this.totalCost = ticketCount * ticketPrice;
		this.bookingDate = LocalDateTime.now();
		this.totalAmount = totalAmount;
		this.noOfSeats = ticketCount;
		this.discountApplied = discount;
		this.movieName = movieName;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	@JsonIgnore
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	public LocalDateTime getShowTime() {
		return showTime;
	}

	public void setShowTime(LocalDateTime showTime) {
		this.showTime = showTime;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public double getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(double discountApplied) {
		this.discountApplied = discountApplied;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
