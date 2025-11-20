package com.devsu.bankingaccount.infrastructure.web;

import com.devsu.bankingaccount.application.dto.MovimientoRequest;
import com.devsu.bankingaccount.application.dto.MovimientoResponse;
import com.devsu.bankingaccount.application.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse registrar(@Valid @RequestBody MovimientoRequest request) {
        return movimientoService.registrarMovimiento(request);
    }
}
