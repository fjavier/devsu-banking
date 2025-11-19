package com.devsu.bankingcustomer.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface PersonaJpaRepository extends JpaRepository<PersonaJpaEntity, Long> {
}
