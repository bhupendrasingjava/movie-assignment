package com.example.kafka.producer.notification.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.kafka.producer.notification.model.Booking;
import com.example.kafka.producer.notification.repository.BookingRepository;

import jakarta.annotation.PostConstruct;

@Component
public class NotificationScheduler {
    
    @PostConstruct 
    public void init() { System.out.println("NotificationScheduler bean initialized");}

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private BookingRepository bookingRepository;

    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void scheduleNotifications() {
        logger.info("Starting scheduled task to read bookings and send notifications.");
        System.out.println("Inside scheduleNotifications method");
        List<Booking> bookings = bookingRepository.findUpcomingBookings();
        System.out.println("Total no of booking count =="+bookings.size());
        logger.debug("Fetched bookings: {}", bookings);

        for (Booking booking : bookings) {
            String message = createNotificationMessage(booking);
            logger.debug("Sending message: {}", message);

            kafkaTemplate.send("sms", message); //Topic name-sms
            kafkaTemplate.send("email", message);  //Topic name-email
        }

        logger.info("Finished scheduled task.");
    }

    private String createNotificationMessage(Booking booking) {
        // Create and return a JSON string or other format for the message
        return "Booking reminder for " + booking.getMovieName() + " at " + booking.getShowTime();
    }
}
