package com.devsu.bankingcustomer.domain.model;

public class Cliente extends Persona {

    private Long clienteInternalId;
    private String clienteId;
    private String contrasena;
    private boolean estado;

    public Cliente() {}

    public Cliente(Long clienteInternalId, String clienteId, String contrasena,
                   boolean estado, Long personaId, String nombre, String genero,
                   Integer edad, String identificacion, String direccion, String telefono) {
        super(personaId, nombre, genero, edad, identificacion, direccion, telefono);
        this.clienteInternalId = clienteInternalId;
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public Long getClienteInternalId() { return clienteInternalId; }
    public void setClienteInternalId(Long clienteInternalId) { this.clienteInternalId = clienteInternalId; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
