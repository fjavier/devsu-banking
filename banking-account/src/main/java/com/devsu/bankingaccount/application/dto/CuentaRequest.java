package com.devsu.bankingaccount.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CuentaRequest(
        @NotEmpty(message = "[numeroCuenta] {message.default.not-empty}")
        String numeroCuenta,
        @NotEmpty(message = "[tipoCuenta] {message.default.not-empty}")
        String tipoCuenta,
        @NotNull(message = "[tipoCuenta] {message.default.not-empty}")
        BigDecimal saldoInicial,
        Boolean estado,
        @NotNull(message = "[clienteId] {message.default.not-empty}")
        Long clienteId
) {}
