package com.devsu.bankingcustomer.application.dto;

import java.io.Serializable;

public record ClienteCreatedEvent(Long clienteId, String nombre) implements Serializable {}

