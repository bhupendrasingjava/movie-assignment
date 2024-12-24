package com.example.kafka.producer.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.kafka.producer.model.booking.Booking;
import com.example.kafka.producer.repository.BookingRepository;

@Service
public class NotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private BookingRepository bookingRepository;

    @Scheduled(fixedRate = 60000) // Run every minute (adjust as needed)
    public void scheduleNotifications() {
        logger.info("Starting scheduled task to read bookings and send notifications.");

        List<Booking> bookings = bookingRepository.findUpcomingBookings();
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
