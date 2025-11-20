package com.devsu.bankingaccount.domain.port;

import com.devsu.bankingaccount.domain.model.Cuenta;

import java.util.Optional;

public interface CuentaRepositoryPort {
    Cuenta save(Cuenta cuenta);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
