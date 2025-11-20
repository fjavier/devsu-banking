package com.devsu.bankingaccount.application.dto.reporte;

import java.math.BigDecimal;

public record MovimientosItem(
	String fecha,
	BigDecimal valor,
	String tipoMovimiento,
    BigDecimal saldo
) {
}
