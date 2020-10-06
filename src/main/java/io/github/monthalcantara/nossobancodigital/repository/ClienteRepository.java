package io.github.monthalcantara.nossobancodigital.repository;

import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findAllByNome(String name, Pageable pageable);

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByCnh(String cnh);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByEnderecoId(Long id);

    Optional<Cliente> findByEndereco(Endereco endereco);

}
