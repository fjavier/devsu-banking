package com.devsu.bankingaccount.infrastructure.persistence;

import com.devsu.bankingaccount.domain.model.Cuenta;
import com.devsu.bankingaccount.domain.port.CuentaRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CuentaPersistenceAdapter implements CuentaRepositoryPort {

    private final CuentaJpaRepository jpaRepository;

    public CuentaPersistenceAdapter(CuentaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        CuentaJpaEntity e = toEntity(cuenta);
        CuentaJpaEntity saved = jpaRepository.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        return jpaRepository.findByNumeroCuenta(numeroCuenta).map(this::toDomain);
    }

    private CuentaJpaEntity toEntity(Cuenta c) {
        CuentaJpaEntity e = new CuentaJpaEntity();
        e.setId(c.getId());
        e.setNumeroCuenta(c.getNumeroCuenta());
        e.setTipoCuenta(c.getTipoCuenta());
        e.setSaldoInicial(c.getSaldoInicial());
        e.setSaldoDisponible(c.getSaldoDisponible());
        e.setEstado(c.getEstado());
        e.setClienteId(c.getClienteId());
        return e;
    }

    private Cuenta toDomain(CuentaJpaEntity e) {
        Cuenta c = new Cuenta();
        c.setId(e.getId());
        c.setNumeroCuenta(e.getNumeroCuenta());
        c.setTipoCuenta(e.getTipoCuenta());
        c.setSaldoInicial(e.getSaldoInicial());
        c.setSaldoDisponible(e.getSaldoDisponible());
        c.setEstado(Boolean.TRUE.equals(e.getEstado()));
        c.setClienteId(e.getClienteId());
        return c;
    }
}
