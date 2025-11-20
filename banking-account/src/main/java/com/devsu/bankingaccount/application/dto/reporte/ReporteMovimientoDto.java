package com.devsu.bankingaccount.application.dto.reporte;

import java.util.List;

public record ReporteMovimientoDto(
        Long clienteId,
        Persona persona,
	List<CuentasItem> cuentas
) {
}