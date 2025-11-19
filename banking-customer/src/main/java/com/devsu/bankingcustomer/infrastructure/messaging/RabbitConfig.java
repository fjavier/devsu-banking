package com.devsu.bankingcustomer.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "cliente.exchange";
    public static final String QUEUE = "cliente.created.queue";
    public static final String ROUTING_KEY = "cliente.created";

    @Bean
    public TopicExchange clienteExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue clienteCreatedQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding clienteCreatedBinding() {
        return BindingBuilder.bind(clienteCreatedQueue())
                .to(clienteExchange())
                .with(ROUTING_KEY);
    }
}
