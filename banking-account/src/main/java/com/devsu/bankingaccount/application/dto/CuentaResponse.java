package com.devsu.bankingaccount.application.dto;

import java.math.BigDecimal;

public record CuentaResponse(
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        BigDecimal saldoDisponible,
        Boolean estado,
        Long clienteId
) {}
