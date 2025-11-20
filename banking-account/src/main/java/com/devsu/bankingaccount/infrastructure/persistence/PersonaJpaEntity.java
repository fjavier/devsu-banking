package com.devsu.bankingaccount.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personas")
public class PersonaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String genero;
    private Integer edad;

    @Column(unique = true, nullable = false, updatable = false)
    private String identificacion;

    private String direccion;
    private String telefono;

    @OneToOne(mappedBy = "persona", fetch = FetchType.LAZY)
    private ClienteJpaEntity cliente;
}
