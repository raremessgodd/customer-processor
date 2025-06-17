package com.mvp.customerprocessor.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfiguration {

    @Value("${rabbitmq.queue.bookings_created_name}")
    private String bookingsCreatedQueueName;

    @Value("${rabbitmq.queue.bookings_cancelled_name}")
    private String bookingsCanceledQueueName;

    @Value("${rabbitmq.queue.bookings_completed_name}")
    private String bookingsCompletedQueueName;

    @Value("${rabbitmq.queue.bookings_modified_name}")
    private String bookingsModifiedQueueName;

    @Value("${rabbitmq.queue.restaurant_name}")
    private String restaurantsQueueName;

    @Value("${rabbitmq.dlq.queue.bookings_created_name}")
    private String bookingsCreatedDlqQueueName;

    @Value("${rabbitmq.dlq.queue.bookings_cancelled_name}")
    private String bookingsCanceledDlqQueueName;

    @Value("${rabbitmq.dlq.queue.bookings_completed_name}")
    private String bookingsCompletedDlqQueueName;

    @Value("${rabbitmq.dlq.queue.bookings_modified_name}")
    private String bookingsModifiedDlqQueueName;

    @Value("${rabbitmq.dlq.queue.restaurant_name}")
    private String restaurantsDlqQueueName;

    @Value("${rabbitmq.key.bookings_created_key}")
    private String bookingsCreatedKey;

    @Value("${rabbitmq.key.bookings_cancelled_key}")
    private String bookingsCanceledKey;

    @Value("${rabbitmq.key.bookings_completed_key}")
    private String bookingsCompletedKey;

    @Value("${rabbitmq.key.bookings_modified_key}")
    private String bookingsModifiedKey;

    @Value("${rabbitmq.key.restaurant_key}")
    private String restaurantsKey;

    @Value("${rabbitmq.exchange.bookings}")
    private String bookingsExchange;

    @Value("${rabbitmq.exchange.restaurant}")
    private String restaurantsExchange;

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(bookingsExchange, true, false);
    }

    @Bean
    public TopicExchange restaurantExchange() {
        return new TopicExchange(restaurantsExchange, true, false);
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return QueueBuilder.durable(bookingsCreatedQueueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", bookingsCreatedDlqQueueName)
                .build();
    }

    @Bean
    public Queue bookingCompletedQueue() {
        return QueueBuilder.durable(bookingsCompletedQueueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", bookingsCompletedDlqQueueName)
                .build();
    }

    @Bean
    public Queue bookingCancelledQueue() {
        return QueueBuilder.durable(bookingsCanceledQueueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", bookingsCanceledDlqQueueName)
                .build();
    }

    @Bean
    public Queue bookingModifiedQueue() {
        return QueueBuilder.durable(bookingsModifiedQueueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", bookingsModifiedDlqQueueName)
                .build();
    }

    @Bean
    public Queue restaurantsQueue() {
        return QueueBuilder.durable(restaurantsQueueName)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", restaurantsDlqQueueName)
                .build();
    }

    @Bean
    public Queue bookingCreatedDLQ() {
        return new Queue(bookingsCreatedDlqQueueName);
    }

    @Bean
    public Queue bookingCompletedDLQ() {
        return new Queue(bookingsCompletedDlqQueueName);
    }

    @Bean
    public Queue bookingCancelledDLQ() {
        return new Queue(bookingsCanceledDlqQueueName);
    }

    @Bean
    public Queue bookingModifiedDLQ() {
        return new Queue(bookingsModifiedDlqQueueName);
    }

    @Bean
    public Queue restaurantDLQ() {
        return new Queue(restaurantsDlqQueueName);
    }

    @Bean
    public Binding bookingCreatedBinding() {
        return BindingBuilder.bind(bookingCreatedQueue())
                .to(bookingExchange())
                .with(bookingsCreatedKey);
    }

    @Bean
    public Binding bookingConfirmedBinding() {
        return BindingBuilder.bind(bookingCompletedQueue())
                .to(bookingExchange())
                .with(bookingsCompletedKey);
    }

    @Bean
    public Binding bookingCancelledBinding() {
        return BindingBuilder.bind(bookingCancelledQueue())
                .to(bookingExchange())
                .with(bookingsCanceledKey);
    }

    @Bean
    public Binding bookingModifiedBinding() {
        return BindingBuilder.bind(bookingModifiedQueue())
                .to(bookingExchange())
                .with(bookingsModifiedKey);
    }

    @Bean
    public Binding restaurantUpdatedBinding() {
        return BindingBuilder.bind(restaurantsQueue())
                .to(restaurantExchange())
                .with(restaurantsKey);
    }
}
