package com.devsu.bankingaccount.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReporteMovimiento (
        Long clienteId, String nombre, String telefono, Integer edad, String genero,
        String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial, BigDecimal saldoDisponible, Boolean estado,
        LocalDateTime fecha, String tipoMovimiento, BigDecimal saldo, BigDecimal valor
) {
}
