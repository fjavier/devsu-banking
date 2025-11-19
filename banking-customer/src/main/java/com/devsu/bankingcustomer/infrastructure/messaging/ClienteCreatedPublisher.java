package com.devsu.bankingcustomer.infrastructure.messaging;

import com.devsu.bankingcustomer.application.dto.ClienteCreatedEvent;
import com.devsu.bankingcustomer.domain.model.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClienteCreatedPublisher {

    private final AmqpTemplate amqpTemplate;

    public ClienteCreatedPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publishClienteCreated(Cliente cliente) {
        ClienteCreatedEvent event = new ClienteCreatedEvent(
                cliente.getId(),
                cliente.getNombre()
        );

        amqpTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );
        log.info("Cliente creado enviado a RabbitMQ: " + event);
    }

}
