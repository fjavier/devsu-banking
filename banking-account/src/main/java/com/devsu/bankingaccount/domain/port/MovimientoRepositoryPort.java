package com.devsu.bankingaccount.domain.port;

import com.devsu.bankingaccount.application.dto.ReporteMovimiento;
import com.devsu.bankingaccount.domain.model.Movimiento;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepositoryPort {
    Movimiento save(Movimiento movimiento);
    List<ReporteMovimiento> findByClienteAndFechaBetween(Long clienteId, LocalDateTime desde, LocalDateTime hasta);
}
