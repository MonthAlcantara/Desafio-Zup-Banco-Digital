package io.github.monthalcantara.nossobancodigital.repository;

import io.github.monthalcantara.nossobancodigital.model.DocumentoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<DocumentoCliente, Long> {
}
