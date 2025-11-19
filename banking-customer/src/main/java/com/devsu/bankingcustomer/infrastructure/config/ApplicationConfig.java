package com.devsu.bankingcustomer.infrastructure.config;

import com.devsu.bankingcustomer.application.service.ClienteService;
import com.devsu.bankingcustomer.domain.port.ClienteRepositoryPort;
import com.devsu.bankingcustomer.infrastructure.messaging.ClienteCreatedPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ClienteService clienteService(ClienteRepositoryPort repoPort,
                                         ClienteCreatedPublisher publisher) {
        return new ClienteService(repoPort, publisher);
    }
}
