package com.devsu.bankingaccount.application.service;

import com.devsu.bankingaccount.application.dto.MovimientoRequest;
import com.devsu.bankingaccount.application.dto.MovimientoResponse;
import com.devsu.bankingaccount.application.dto.ReporteMovimiento;
import com.devsu.bankingaccount.application.dto.reporte.CuentasItem;
import com.devsu.bankingaccount.application.dto.reporte.MovimientosItem;
import com.devsu.bankingaccount.application.dto.reporte.Persona;
import com.devsu.bankingaccount.application.dto.reporte.ReporteMovimientoDto;
import com.devsu.bankingaccount.domain.exception.SaldoNoDisponibleException;
import com.devsu.bankingaccount.domain.model.Cuenta;
import com.devsu.bankingaccount.domain.model.Movimiento;
import com.devsu.bankingaccount.domain.port.CuentaRepositoryPort;
import com.devsu.bankingaccount.domain.port.MovimientoRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovimientoService {

    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final MovimientoRepositoryPort movimientoRepositoryPort;

    public MovimientoService(CuentaRepositoryPort cuentaRepositoryPort,
                             MovimientoRepositoryPort movimientoRepositoryPort) {
        this.cuentaRepositoryPort = cuentaRepositoryPort;
        this.movimientoRepositoryPort = movimientoRepositoryPort;
    }

    @Transactional
    public MovimientoResponse registrarMovimiento(MovimientoRequest request) {
        Cuenta cuenta = cuentaRepositoryPort.findByNumeroCuenta(request.numeroCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        BigDecimal valor = request.valor();
        BigDecimal nuevoSaldo = cuenta.getSaldoDisponible().add(valor);

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepositoryPort.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(request.tipoMovimiento());
        movimiento.setValor(valor);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuentaId(cuenta.getId());

        Movimiento saved = movimientoRepositoryPort.save(movimiento);

        return new MovimientoResponse(
                saved.getFecha(),
                cuenta.getNumeroCuenta(),
                saved.getTipoMovimiento(),
                saved.getValor(),
                saved.getSaldo()
        );
    }

    public ReporteMovimientoDto generarReporte(Long clienteId,
                                                      LocalDateTime desde,
                                                      LocalDateTime hasta) {

        List<ReporteMovimiento> movimientos = movimientoRepositoryPort
                .findByClienteAndFechaBetween(clienteId, desde, hasta);

        ReporteMovimiento first;
                try {
                    first = movimientos.get(0);
                }catch (Exception e) {
                    throw new RuntimeException("No se encontraron registros con los parametros establecidos");
                }


        Persona persona = new Persona(
                first.nombre(),
                first.genero(),
                first.telefono(),
                first.edad()
        );

        Map<String, List<ReporteMovimiento>> cuentasMap =
                movimientos.stream().collect(Collectors.groupingBy(ReporteMovimiento::numeroCuenta));

        List<CuentasItem> cuentas = cuentasMap.entrySet().stream()
                .map(entry -> {

                    String numeroCuenta = entry.getKey();
                    List<ReporteMovimiento> movimientosDeCuenta = entry.getValue();

                    ReporteMovimiento cuenta = movimientosDeCuenta.get(0);

                    List<MovimientosItem> movimientosItems = movimientosDeCuenta.stream()
                            .map(m -> new MovimientosItem(
                                    m.fecha().toString(),
                                    m.valor(),
                                    m.tipoMovimiento(),
                                    m.saldo()
                            ))
                            .collect(Collectors.toList());

                    return new CuentasItem(
                            numeroCuenta,
                            cuenta.saldoDisponible(),
                            cuenta.estado(),
                            cuenta.tipoCuenta(),
                            cuenta.saldoInicial(),
                            movimientosItems
                    );

                }).collect(Collectors.toList());

        return new ReporteMovimientoDto(
                first.clienteId(),
                persona,
                cuentas
        );
    }
}
