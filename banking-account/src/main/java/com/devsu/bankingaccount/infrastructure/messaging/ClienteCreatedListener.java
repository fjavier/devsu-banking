package com.devsu.bankingaccount.infrastructure.messaging;

import com.devsu.bankingaccount.application.dto.ClienteCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class ClienteCreatedListener {


    @RabbitListener(queues = RabbitConfig.CLIENTE_CREATED_QUEUE)
    public void onClienteCreated(String event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ClienteCreatedEvent clienteCreatedEvent = mapper.readValue(event, ClienteCreatedEvent.class);
        System.out.println("Cliente creado recibido en banking-account: " + event);
    }
}
