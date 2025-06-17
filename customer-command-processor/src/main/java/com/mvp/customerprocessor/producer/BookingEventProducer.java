package com.mvp.customerprocessor.producer;

import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;
import com.mvp.customerprocessor.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingEventProducer {

    private final RabbitTemplate template;

    @Value("${rabbitmq.exchange.bookings}")
    private String exchange;

    @Value("${rabbitmq.key.bookings_created_key}")
    private String bookingsCreatedKey;

    @Value("${rabbitmq.key.bookings_cancelled_key}")
    private String bookingsCanceledKey;

    @Value("${rabbitmq.key.bookings_completed_key}")
    private String bookingsCompletedKey;

    @Value("${rabbitmq.key.bookings_modified_key}")
    private String bookingsModifiedKey;

    public void sendMessage(BookingCreatedEvent event) {
        send(event, bookingsCreatedKey);
    }

    public void sendMessage(BookingCancelledEvent event) {
        send(event, bookingsCanceledKey);
    }

    public void sendMessage(BookingModifiedEvent event) {
        send(event, bookingsModifiedKey);
    }

    public void sendMessage(BookingCompletedEvent event) {
        send(event, bookingsCompletedKey);
    }

    private <T> void send(Event event, String routingKey) {
        template.convertAndSend(exchange, routingKey, event);
        log.info("Отправлено уведомление о бронировании в {}: {}", exchange, event);
    }
}
