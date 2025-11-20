package com.devsu.bankingaccount.infrastructure.config;

import com.devsu.bankingaccount.application.service.CuentaService;
import com.devsu.bankingaccount.application.service.MovimientoService;
import com.devsu.bankingaccount.domain.port.CuentaRepositoryPort;
import com.devsu.bankingaccount.domain.port.MovimientoRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public MovimientoService movimientoService(CuentaRepositoryPort cuentaRepositoryPort,
                                               MovimientoRepositoryPort movimientoRepositoryPort) {
        return new MovimientoService(cuentaRepositoryPort, movimientoRepositoryPort);
    }

    @Bean
    public CuentaService cuentaService(CuentaRepositoryPort cuentaRepositoryPort, MovimientoService movimientoService) {
        return new CuentaService(cuentaRepositoryPort, movimientoService);
    }
}
