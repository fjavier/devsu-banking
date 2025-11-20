package com.devsu.bankingaccount.infrastructure.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CLIENTE_CREATED_QUEUE = "cliente.created.queue";

    @Bean
    public Queue clienteCreatedQueue() {
        return new Queue(CLIENTE_CREATED_QUEUE, true);
    }
}
