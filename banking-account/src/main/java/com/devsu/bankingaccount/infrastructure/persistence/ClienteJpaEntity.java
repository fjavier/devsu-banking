package com.devsu.bankingaccount.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
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

    @OneToMany(mappedBy = "cliente")
    @Fetch(FetchMode.SUBSELECT)
    private List<CuentaJpaEntity> cuentas;

}
