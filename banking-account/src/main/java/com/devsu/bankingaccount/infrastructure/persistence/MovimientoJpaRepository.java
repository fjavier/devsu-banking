package com.devsu.bankingaccount.infrastructure.persistence;

import com.devsu.bankingaccount.application.dto.ReporteMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoJpaRepository extends JpaRepository<MovimientoJpaEntity, Long> {

    @Query("""
        select new com.devsu.bankingaccount.application.dto.ReporteMovimiento(
                cliente.id, persona.nombre, persona.telefono,
                persona.edad, persona.genero, cuentas.numeroCuenta, cuentas.tipoCuenta, cuentas.saldoInicial, cuentas.saldoDisponible, cuentas.estado,
                        mov.fecha, mov.tipoMovimiento, mov.saldo, mov.valor)
        from ClienteJpaEntity cliente
        join  cliente.persona persona
        join  cliente.cuentas cuentas
        join  cuentas.movimientos mov
        where cliente.id = :clienteId
          and mov.fecha between :desde and :hasta
        """)
    List<ReporteMovimiento> findByClienteAndFechaBetween(
            Long clienteId,
            LocalDateTime desde,
            LocalDateTime hasta
    );
}
