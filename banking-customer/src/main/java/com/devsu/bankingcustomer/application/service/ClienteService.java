package com.devsu.bankingcustomer.application.service;

import com.devsu.bankingcustomer.application.dto.ClienteRequest;
import com.devsu.bankingcustomer.application.dto.ClienteResponse;
import com.devsu.bankingcustomer.domain.model.Cliente;
import com.devsu.bankingcustomer.domain.port.ClienteRepositoryPort;
import com.devsu.bankingcustomer.infrastructure.messaging.ClienteCreatedPublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ClienteService {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ClienteCreatedPublisher clienteCreatedPublisher;

    public ClienteService(ClienteRepositoryPort clienteRepositoryPort,
                          ClienteCreatedPublisher clienteCreatedPublisher) {
        this.clienteRepositoryPort = clienteRepositoryPort;
        this.clienteCreatedPublisher = clienteCreatedPublisher;
    }


    public ClienteResponse crearCliente(ClienteRequest request) {
        Cliente cliente = new Cliente(
                null,
                request.clienteId(),
                request.contrasena(),
                request.estado() != null ? request.estado() : true,
                null,
                request.nombre(),
                request.genero(),
                request.edad(),
                request.identificacion(),
                request.direccion(),
                request.telefono()
        );

        Cliente saved = clienteRepositoryPort.save(cliente);
        clienteCreatedPublisher.publishClienteCreated(saved);
        log.info("Cliente creado satisfactoriamente");
        return toResponse(saved);
    }

    public List<ClienteResponse> listarClientes() {
        return clienteRepositoryPort.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ClienteResponse obtenerPorId(Integer clienteId) {
        Cliente cliente = clienteRepositoryPort.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return toResponse(cliente);
    }


    public ClienteResponse actualizarCliente(Integer clienteId, ClienteRequest request) {
        Cliente existing = clienteRepositoryPort.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        applyPatch(existing, request);

        Cliente saved = clienteRepositoryPort.save(existing);
        return toResponse(saved);
    }

    private void applyPatch(Cliente existing, ClienteRequest req) {

        Optional.ofNullable(req.nombre()).ifPresent(existing::setNombre);
        Optional.ofNullable(req.genero()).ifPresent(existing::setGenero);
        Optional.ofNullable(req.edad()).ifPresent(existing::setEdad);
        Optional.ofNullable(req.direccion()).ifPresent(existing::setDireccion);
        Optional.ofNullable(req.telefono()).ifPresent(existing::setTelefono);
        Optional.ofNullable(req.contrasena()).ifPresent(existing::setContrasena);
        Optional.ofNullable(req.estado()).ifPresent(existing::setEstado);
    }


    public void eliminarCliente(Integer clienteId) {
        clienteRepositoryPort.deleteByClienteId(clienteId);
    }

    private ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(
                c.getId(),
                c.getNombre(),
                c.getGenero(),
                c.getEdad(),
                c.getIdentificacion(),
                c.getDireccion(),
                c.getTelefono(),
                c.isEstado()
        );
    }
}
