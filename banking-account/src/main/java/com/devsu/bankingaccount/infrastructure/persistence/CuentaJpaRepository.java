package com.devsu.bankingaccount.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaJpaRepository extends JpaRepository<CuentaJpaEntity, Long> {
    Optional<CuentaJpaEntity> findByNumeroCuenta(String numeroCuenta);
}
