package com.devsu.bankingaccount.infrastructure.persistence;

import com.devsu.bankingaccount.application.dto.ReporteMovimiento;
import com.devsu.bankingaccount.domain.model.Movimiento;
import com.devsu.bankingaccount.domain.port.MovimientoRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MovimientoPersistenceAdapter implements MovimientoRepositoryPort {

    private final MovimientoJpaRepository jpaRepository;

    public MovimientoPersistenceAdapter(MovimientoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Movimiento save(Movimiento movimiento) {
        MovimientoJpaEntity e = new MovimientoJpaEntity();
        e.setFecha(movimiento.getFecha());
        e.setTipoMovimiento(movimiento.getTipoMovimiento());
        e.setValor(movimiento.getValor());
        e.setSaldo(movimiento.getSaldo());
        e.setCuentaId(movimiento.getCuentaId());
        MovimientoJpaEntity saved = jpaRepository.save(e);

        movimiento.setId(saved.getId());
        return movimiento;
    }

    @Override
    public List<ReporteMovimiento> findByClienteAndFechaBetween(
            Long clienteId, LocalDateTime desde, LocalDateTime hasta) {

        return jpaRepository.findByClienteAndFechaBetween(clienteId, desde, hasta);
    }
}
