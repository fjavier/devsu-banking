package com.devsu.bankingcustomer.application.service;

import com.devsu.bankingcustomer.application.dto.ClienteRequest;
import com.devsu.bankingcustomer.application.dto.ClienteResponse;
import com.devsu.bankingcustomer.domain.model.Cliente;
import com.devsu.bankingcustomer.domain.port.ClienteRepositoryPort;
import com.devsu.bankingcustomer.infrastructure.messaging.ClienteCreatedPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



    @ExtendWith(MockitoExtension.class)
    class ClienteServiceTest {

        @Mock
        private ClienteRepositoryPort repository;

        @Mock
        private ClienteCreatedPublisher publisher;

        @InjectMocks
        private ClienteService service;

        // ------------------------------------------------------------
        @Test
        void crearCliente_debeGuardarYPublicarEvento() {
            ClienteRequest request = new ClienteRequest(
                    "nombre", "1234", 21, "Juan", "M", "3000001",
                    "001-111", "Managua", true
            );

            Cliente saved = new Cliente(
                    1L, "1L", "1234", true, 99L,
                    "Juan", "M", 30, "001-111", "Managua", "88888888"
            );

            when(repository.save(any())).thenReturn(saved);

            ClienteResponse response = service.crearCliente(request);

            assertEquals(saved.getId(), response.clienteId());
            verify(repository).save(any());
            verify(publisher).publishClienteCreated(saved);
        }

        // ------------------------------------------------------------
        @Test
        void listarClientes_debeRetornarLista() {
            Cliente c1 = new Cliente();
            c1.setId(1L);
            c1.setNombre("A");

            Cliente c2 = new Cliente();
            c2.setId(2L);
            c2.setNombre("B");

            when(repository.findAll()).thenReturn(List.of(c1, c2));

            List<ClienteResponse> result = service.listarClientes();

            assertEquals(2, result.size());
            assertEquals("A", result.get(0).nombre());
            assertEquals("B", result.get(1).nombre());
        }

        // ------------------------------------------------------------
        @Test
        void obtenerPorId_existente_debeRetornarCliente() {
            Cliente cliente = new Cliente();
            cliente.setId(1L);
            cliente.setNombre("Juan");

            when(repository.findByClienteId(1)).thenReturn(Optional.of(cliente));

            ClienteResponse response = service.obtenerPorId(1);

            assertEquals(1, response.clienteId());
            assertEquals("Juan", response.nombre());
        }

        @Test
        void obtenerPorId_noExistente_debeLanzarExcepcion() {
            when(repository.findByClienteId(1)).thenReturn(Optional.empty());
            assertThrows(RuntimeException.class, () -> service.obtenerPorId(1));
        }

        // ------------------------------------------------------------
        @Test
        void actualizarCliente_debeModificarSoloCamposPresentes() {
            Cliente existing = new Cliente(
                    1L, "10", "1234", true, 99L,
                    "Juan", "M", 20, "001", "Dir", "7777"
            );

            when(repository.findByClienteId(1)).thenReturn(Optional.of(existing));

            ClienteRequest patch = new ClienteRequest(
                    null, null, null,
                    "Pedro", null, null,
                    null, null, true
            );

            Cliente saved = new Cliente(
                    1L, "10", "1234", true, 99L,
                    "Pedro", "M", 20, "001", "Dir", "9999"
            );

            when(repository.save(existing)).thenReturn(saved);

            ClienteResponse response = service.actualizarCliente(1, patch);

            assertEquals("Pedro", response.nombre());
            assertEquals("9999", response.telefono());
            assertEquals("M", response.genero());
            assertEquals(20, response.edad());
        }

        // ------------------------------------------------------------
        @Test
        void eliminarCliente_debeInvocarRepositorio() {
            service.eliminarCliente(5);
            verify(repository).deleteByClienteId(5);
        }
    }
