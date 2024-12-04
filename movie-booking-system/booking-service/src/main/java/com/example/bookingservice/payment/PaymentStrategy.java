package com.example.bookingservice.payment;

public interface PaymentStrategy {
	
	boolean processPayment(Long userId, double amount);

}
