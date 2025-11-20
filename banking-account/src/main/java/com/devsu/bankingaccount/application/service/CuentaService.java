package com.devsu.bankingaccount.application.service;

import com.devsu.bankingaccount.application.dto.CuentaRequest;
import com.devsu.bankingaccount.application.dto.CuentaResponse;
import com.devsu.bankingaccount.application.dto.CuentaUpdateRequest;
import com.devsu.bankingaccount.application.dto.MovimientoRequest;
import com.devsu.bankingaccount.domain.model.Cuenta;
import com.devsu.bankingaccount.domain.port.CuentaRepositoryPort;

import java.math.BigDecimal;


public class CuentaService {
    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final MovimientoService movimientoService;

    public CuentaService(CuentaRepositoryPort cuentaRepositoryPort, MovimientoService movimientoService) {
        this.cuentaRepositoryPort = cuentaRepositoryPort;
        this.movimientoService = movimientoService;
    }

    public CuentaResponse crearCuenta(CuentaRequest request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(request.numeroCuenta());
        cuenta.setTipoCuenta(request.tipoCuenta());
        cuenta.setSaldoInicial(request.saldoInicial());
        cuenta.setSaldoDisponible(BigDecimal.ZERO);
        cuenta.setEstado(request.estado() != null ? request.estado() : true);
        cuenta.setClienteId(request.clienteId());

        Cuenta saved = cuentaRepositoryPort.save(cuenta);

        MovimientoRequest movimientoRequest =
                new MovimientoRequest(saved.getNumeroCuenta(), "CREACION", request.saldoInicial());
        movimientoService.registrarMovimiento(movimientoRequest);
        return toResponse(saved);
    }

    public CuentaResponse obtenerCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaRepositoryPort.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        return toResponse(cuenta);
    }

    public CuentaResponse actualizarCuenta(String numeroCuenta, CuentaUpdateRequest request) {
        Cuenta cuenta = cuentaRepositoryPort.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (request.tipoCuenta() != null)
            cuenta.setTipoCuenta(request.tipoCuenta());

        if (request.estado() != null)
            cuenta.setEstado(request.estado());

        Cuenta saved = cuentaRepositoryPort.save(cuenta);
        return toResponse(saved);
    }

    private CuentaResponse toResponse(Cuenta c) {
        return new CuentaResponse(
                c.getNumeroCuenta(), c.getTipoCuenta(),
                c.getSaldoInicial(), c.getSaldoDisponible(),
                c.getEstado(), c.getClienteId()
        );
    }
}
