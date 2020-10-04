package io.github.monthalcantara.nossobancodigital.repository;

import io.github.monthalcantara.nossobancodigital.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Optional<Endereco> findByCep(String cep);
}
