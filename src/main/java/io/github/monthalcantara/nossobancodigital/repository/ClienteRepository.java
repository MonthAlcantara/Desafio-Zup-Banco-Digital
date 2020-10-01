package io.github.monthalcantara.nossobancodigital.repository;

import io.github.monthalcantara.nossobancodigital.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
