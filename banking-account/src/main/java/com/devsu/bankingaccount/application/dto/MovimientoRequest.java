package com.devsu.bankingaccount.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MovimientoRequest(
        @NotEmpty(message = "[numeroCuenta] {message.default.not-empty}")
        String numeroCuenta,
        @NotEmpty(message = "[tipoMovimiento] {message.default.not-empty}")
        @NotEmpty
        String tipoMovimiento,
        @NotNull(message = "[valor] {message.default.not-empty}")
        BigDecimal valor
) {}
