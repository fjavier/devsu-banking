package com.devsu.bankingaccount.application.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;

public record CuentaUpdateRequest(
        String tipoCuenta,
        Boolean estado) {
}
