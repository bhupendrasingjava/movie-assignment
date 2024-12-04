package com.example.bookingservice.config;

import com.example.bookingservice.factory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public BookingFactory ticketCounterBookingFactory() {
        return new TicketCounterBookingFactory();
    }

    @Bean
    public BookingFactory paytmBookingFactory() {
        return new PaytmBookingFactory();
    }

    @Bean
    public BookingFactory bookingAppBookingFactory() {
        return new BookingAppBookingFactory();
    }
}
