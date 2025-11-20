package com.devsu.bankingaccount.application.dto.reporte;

import java.math.BigDecimal;
import java.util.List;

public record CuentasItem(
        String numeroCuenta,
        BigDecimal saldoDisponible,
    Boolean estado,
    String tipoCuenta,
	BigDecimal saldoInicial,
	List<MovimientosItem> movimientos
    ) {
}