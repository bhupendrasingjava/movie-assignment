package com.example.bookingservice.payment;

import org.springframework.stereotype.Component;

@Component
public class WalletPayment implements PaymentStrategy {

	@Override
	public boolean processPayment(Long userId, double amount) {
		// Logic for wallet payment processing
		System.out.println("Processing Wallet payment for userId: " + userId + ", amount: " + amount);
		return true; // Assume payment is always successful
	}
}
