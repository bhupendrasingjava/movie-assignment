package com.example.bookingservice.payment;

import org.springframework.stereotype.Component;

@Component
public class NetbankingPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(Long userId, double amount) {
        // Logic for net banking payment processing
        System.out.println("Processing Netbanking payment for userId: " + userId + ", amount: " + amount);
        return true; // Assume payment is always successful
    }
}

