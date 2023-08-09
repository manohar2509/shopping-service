package com.shopping.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class NotificationServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceApplication.class);
    public static void main(String[] args){
        SpringApplication.run(NotificationServiceApplication.class,args);
    }
    @KafkaListener(topics="notificationTopic",groupId = "notificationId")
    public void consume(String orderNumber){
        LOGGER.info(String.format("Message received -> %s", orderNumber));
    }
}
