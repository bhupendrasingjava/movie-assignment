package com.example.bookingservice.payment;


import org.springframework.stereotype.Component;

@Component
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(Long userId, double amount) {
        // Logic for credit card payment processing
        System.out.println("Processing Credit Card payment for userId: " + userId + ", amount: " + amount);
        return true; // Assume payment is always successful
    }
}

