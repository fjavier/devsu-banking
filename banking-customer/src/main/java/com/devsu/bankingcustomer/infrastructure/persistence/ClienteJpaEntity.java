package com.devsu.bankingcustomer.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClienteJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;


    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private PersonaJpaEntity persona;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }



    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public PersonaJpaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaJpaEntity persona) {
        this.persona = persona;
    }
}
