package com.devsu.bankingcustomer.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClienteRequest(
        @NotEmpty(message = "[nombre] {message.default.not-empty}")
        String nombre,

        @Pattern(regexp = "^[M|F]$", message = "[genero] {message.default.pattern}")
        @NotEmpty(message = "[genero] {message.default.not-empty}")
        String genero,
        @NotNull(message = "[edad] {message.default.not-empty}")
        Integer edad,
        @NotEmpty(message = "[identificacion] {message.default.not-empty}")
        String identificacion,
        @NotEmpty(message = "[direccion] {message.default.not-empty}")
        String direccion,
        @NotEmpty(message = "[telefono] {message.default.not-empty}")
        String telefono,
        String clienteId,
        @NotEmpty(message = "[contrasena] {message.default.not-empty}")
        String contrasena,
        Boolean estado
) {}
