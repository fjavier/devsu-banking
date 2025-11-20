package com.devsu.bankingaccount.infrastructure.web;

import com.devsu.bankingaccount.application.dto.CuentaRequest;
import com.devsu.bankingaccount.application.dto.CuentaResponse;
import com.devsu.bankingaccount.application.dto.CuentaUpdateRequest;
import com.devsu.bankingaccount.application.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponse crear(@Valid @RequestBody CuentaRequest request) {
        return cuentaService.crearCuenta(request);
    }

    @GetMapping("/{numeroCuenta}")
    public CuentaResponse obtener(@PathVariable String numeroCuenta) {
        return cuentaService.obtenerCuenta(numeroCuenta);
    }

    @PutMapping("/{numeroCuenta}")
    public CuentaResponse actualizar(@PathVariable String numeroCuenta,
                                     @Valid @RequestBody CuentaUpdateRequest request) {
        return cuentaService.actualizarCuenta(numeroCuenta, request);
    }
}
