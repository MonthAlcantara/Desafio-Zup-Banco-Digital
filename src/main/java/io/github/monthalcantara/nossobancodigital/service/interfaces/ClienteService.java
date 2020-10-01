package io.github.monthalcantara.nossobancodigital.service.interfaces;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {
    Page<Cliente> busqueTodosClientes(Pageable pageable);

    ClienteResponseDTO atualizeClientePeloId(Long id, ClienteDTO cliente);

    ClienteResponseDTO busqueClientePeloId(Long id);

    ClienteResponseDTO busqueClientePeloCPF(String cpf);

    ClienteResponseDTO busqueClientePelaCNH(String cnh);

    Page<ClienteResponseDTO> busqueClientePeloNome(String name, Pageable pageable);

    ClienteResponseDTO salve(ClienteDTO cliente);

    void deleteClientePeloId(Integer id);
}
