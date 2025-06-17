package com.mvp.customerprocessor.producer;

import com.mvp.customerprocessor.event.RestaurantEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantEventProducer {

    private final RabbitTemplate template;

    @Value("${rabbitmq.exchange.restaurant}")
    private String exchange;

    @Value("${rabbitmq.key.restaurant_key}")
    private String restaurantsKey;

    public void sendMessage(RestaurantEventDto event) {
        template.convertAndSend(exchange, restaurantsKey, event);
        log.info("Отправлено уведомление о изменении ресторана в {}: {}", exchange, event);
    }
}
