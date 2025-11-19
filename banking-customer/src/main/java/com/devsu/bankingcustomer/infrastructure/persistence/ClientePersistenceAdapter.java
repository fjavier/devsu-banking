package com.devsu.bankingcustomer.infrastructure.persistence;

import com.devsu.bankingcustomer.domain.model.Cliente;
import com.devsu.bankingcustomer.domain.port.ClienteRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ClientePersistenceAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository clienteRepo;
    private final PersonaJpaRepository personaRepo;

    public ClientePersistenceAdapter(ClienteJpaRepository clienteRepo,
                                     PersonaJpaRepository personaRepo) {
        this.clienteRepo = clienteRepo;
        this.personaRepo = personaRepo;
    }

    @Transactional
    @Override
    public Cliente save(Cliente cliente) {
        log.info("Guardando cliente {}", cliente);
        PersonaJpaEntity persona = toPersonaEntity(cliente);
        PersonaJpaEntity personaSaved = personaRepo.save(persona);

        ClienteJpaEntity entity = toClienteEntity(cliente, personaSaved);
        ClienteJpaEntity saved = clienteRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Cliente> findByClienteId(Integer clienteId) {
        log.info("Buscando cliente {}", clienteId);
        return clienteRepo.findById(clienteId)
                .map(this::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        log.info("Obteniendo todos los clientes");

        return clienteRepo.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Transactional
    @Override
    public void deleteByClienteId(Integer clienteId) {
        log.info("Eliminando cliente {}", clienteId);
        clienteRepo.deleteById(clienteId);
    }

    // -----------------------------------------------------
    // Helpers de conversión
    // -----------------------------------------------------

    private PersonaJpaEntity toPersonaEntity(Cliente c) {
        PersonaJpaEntity p = new PersonaJpaEntity();
        p.setId(c.getId());
        p.setNombre(c.getNombre());
        p.setGenero(c.getGenero());
        p.setEdad(c.getEdad());
        p.setIdentificacion(c.getIdentificacion());
        p.setDireccion(c.getDireccion());
        p.setTelefono(c.getTelefono());
        return p;
    }

    private ClienteJpaEntity toClienteEntity(Cliente domain, PersonaJpaEntity personaSaved) {
        ClienteJpaEntity e = new ClienteJpaEntity();
        e.setId(domain.getClienteInternalId());
        e.setContrasena(domain.getContrasena());
        e.setEstado(domain.isEstado());
        e.setPersona(personaSaved);
        return e;
    }

    private Cliente toDomain(ClienteJpaEntity e) {

        PersonaJpaEntity p = e.getPersona();

        Cliente c = new Cliente();
        c.setClienteInternalId(e.getId());
        c.setContrasena(e.getContrasena());
        c.setEstado(e.getEstado());

        c.setId(e.getId());
        c.setNombre(p.getNombre());
        c.setGenero(p.getGenero());
        c.setEdad(p.getEdad());
        c.setIdentificacion(p.getIdentificacion());
        c.setDireccion(p.getDireccion());
        c.setTelefono(p.getTelefono());

        return c;
    }
}
