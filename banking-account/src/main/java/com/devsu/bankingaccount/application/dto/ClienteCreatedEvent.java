package com.devsu.bankingaccount.application.dto;

import java.io.Serializable;

public record ClienteCreatedEvent(Long clienteId, String nombre) implements Serializable {}

