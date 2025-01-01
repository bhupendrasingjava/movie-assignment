package com.example.kafka.notification.consumer;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Component
@EnableScheduling
public class NotificationConsumer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TwilioConfiguration twilioConfig;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    @KafkaListener(topics = {"sms", "email"}, groupId = "notification-consumer-group")
    public void listen(ConsumerRecord<String, String> record) {
        boolean success = false;
        int retries = 0;
        while (!success && retries < 3) {
            try {
                if (record.topic().equals("email")) {
                    sendEmail(record.value());
                } else if (record.topic().equals("sms")) {
                    sendSms(record.value());
                }
                success = true;
            } catch (Exception e) {
                retries++;
                if (retries == 3) {
                    kafkaTemplate.send("dlq.notifications", record.value());
                }
            }
        }
    }

    private void sendEmail(String message) throws IOException {
        Email from = new Email("bhupendra.java.developer@gmail.com");
        String subject = "Notification";
        Email to = new Email("bhupendra.java.mobility@gmail.com");
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);
        System.out.println("SendGrid API Key=="+System.getenv("SENDGRID_API_KEY"));
     //   SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

    private void sendSms(String message) {
    	System.out.println("SMS message="+message);
    /*    Message.creator(
            new PhoneNumber("+919456667341"),
            new PhoneNumber(twilioConfig.getPhoneNumber()),
            message
        ).create();
        
        */
    }
    
  
}
