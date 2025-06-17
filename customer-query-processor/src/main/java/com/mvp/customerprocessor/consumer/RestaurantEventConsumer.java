package com.mvp.customerprocessor.consumer;

import com.mvp.customerprocessor.event.RestaurantEventDto;
import com.mvp.customerprocessor.service.restaurant.RestaurantProjectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantEventConsumer {

    private final RestaurantProjectorService restaurantProjectorService;

    @RabbitListener(queues = "${rabbitmq.queue.restaurant_name}")
    public void handle(RestaurantEventDto event) {
        log.info("Получена запись о ресторане: {}", event);
        restaurantProjectorService.save(event);
    }
}
