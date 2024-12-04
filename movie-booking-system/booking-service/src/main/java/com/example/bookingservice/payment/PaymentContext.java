package com.example.bookingservice.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentContext {

    private PaymentStrategy paymentStrategy;

    @Autowired
    public PaymentContext(UPIPayment upiPayment, NetbankingPayment netbankingPayment, CreditCardPayment creditCardPayment, WalletPayment walletPayment) {
        // Default payment strategy
        this.paymentStrategy = upiPayment;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(Long userId, double amount) {
        return paymentStrategy.processPayment(userId, amount);
    }
}
