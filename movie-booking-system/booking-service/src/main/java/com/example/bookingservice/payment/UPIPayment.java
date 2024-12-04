package com.example.bookingservice.payment;


import org.springframework.stereotype.Component;

@Component
public class UPIPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(Long userId, double amount) {
        // Logic for UPI payment processing
        System.out.println("Processing UPI payment for userId: " + userId + ", amount: " + amount);
        return true; // Assume payment is always successful
    }
}

