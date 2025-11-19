package com.devsu.bankingcustomer.application.dto;

public record ClienteResponse(
        Long clienteId,
        String nombre,
        String genero,
        Integer edad,
        String identificacion,
        String direccion,
        String telefono,
        Boolean estado
) {}
