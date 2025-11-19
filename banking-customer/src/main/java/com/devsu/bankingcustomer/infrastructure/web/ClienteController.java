package com.devsu.bankingcustomer.infrastructure.web;

import com.devsu.bankingcustomer.application.dto.ClienteRequest;
import com.devsu.bankingcustomer.application.dto.ClienteResponse;
import com.devsu.bankingcustomer.application.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crear(@RequestBody @Valid ClienteRequest request) {
        return service.crearCliente(request);
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return service.listarClientes();
    }

    @GetMapping("/{clienteId}")
    public ClienteResponse obtener(@PathVariable Integer clienteId) {
        return service.obtenerPorId(clienteId);
    }

    @PutMapping("/{clienteId}")
    public ClienteResponse actualizar(@PathVariable Integer clienteId,
                                      @RequestBody ClienteRequest request) {
        return service.actualizarCliente(clienteId, request);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer clienteId) {
        service.eliminarCliente(clienteId);
    }
}
