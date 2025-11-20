package com.devsu.bankingaccount.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaldoNoDisponibleExceptionTest {

    @Test
    void messageIsSet() {
        SaldoNoDisponibleException ex = new SaldoNoDisponibleException("Saldo no disponible");
        assertEquals("Saldo no disponible", ex.getMessage());
    }
}
