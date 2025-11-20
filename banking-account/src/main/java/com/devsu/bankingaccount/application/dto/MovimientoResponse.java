package com.devsu.bankingaccount.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponse(
        LocalDateTime fecha,
        String numeroCuenta,
        String tipoMovimiento,
        BigDecimal valor,
        BigDecimal saldo
) {}
