package com.example.kafka.notification.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfiguration {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String phoneNumber;

    @Bean
    public String getAccountSid() {
        return accountSid;
    }

    @Bean
    public String getAuthToken() {
        return authToken;
    }

    @Bean
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
