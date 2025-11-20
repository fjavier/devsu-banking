package com.devsu.bankingaccount.infrastructure.messaging;

import com.devsu.bankingaccount.application.dto.ClienteCreatedEvent;
import com.devsu.bankingaccount.application.dto.CuentaRequest;
import com.devsu.bankingaccount.application.service.CuentaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
public class ClienteCreatedListener {

    private final CuentaService cuentaService;

    @RabbitListener(queues = RabbitConfig.CLIENTE_CREATED_QUEUE)
    public void onClienteCreated(String event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ClienteCreatedEvent clienteCreatedEvent = mapper.readValue(event, ClienteCreatedEvent.class);

        CuentaRequest cuentaInicial = new CuentaRequest(UUID.randomUUID().toString().substring(0, 8), "DIGITAL", BigDecimal.ZERO, true,
                clienteCreatedEvent.clienteId());
        cuentaService.crearCuenta(cuentaInicial);
        log.info("Se crea cuenta inicial para cliente:  {}" , clienteCreatedEvent.clienteId());
    }
}
