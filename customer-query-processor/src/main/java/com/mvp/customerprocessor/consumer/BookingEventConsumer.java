package com.mvp.customerprocessor.consumer;

import com.mvp.customerprocessor.event.BookingCancelledEvent;
import com.mvp.customerprocessor.event.BookingCompletedEvent;
import com.mvp.customerprocessor.event.BookingCreatedEvent;
import com.mvp.customerprocessor.event.BookingModifiedEvent;
import com.mvp.customerprocessor.service.booking.BookingProjectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final BookingProjectorService bookingProjectorService;

    @RabbitListener(queues = "${rabbitmq.queue.bookings_created_name}")
    public void handleCreate(BookingCreatedEvent event) {
        log.info("Получена запись о создании брони: {}", event);
        bookingProjectorService.save(event);
    }

    @RabbitListener(queues = "${rabbitmq.queue.bookings_cancelled_name}")
    public void handleCancel(BookingCancelledEvent event) {
        log.info("Получена запись об отмене брони: {}", event);
        bookingProjectorService.cancel(event);
    }

    @RabbitListener(queues = "${rabbitmq.queue.bookings_completed_name}")
    public void handleComplete(BookingCompletedEvent event) {
        log.info("Получена запись о завершении брони: {}", event);
        bookingProjectorService.complete(event);
    }

    @RabbitListener(queues = "${rabbitmq.queue.bookings_modified_name}")
    public void handleModify(BookingModifiedEvent event) {
        log.info("Получена запись о изменении брони: {}", event);
        bookingProjectorService.modify(event);
    }
}
