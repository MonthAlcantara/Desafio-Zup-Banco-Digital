package io.github.monthalcantara.nossobancodigital.service.interfaces;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    Page<ClienteResponseDTO> busqueTodosClientes(Pageable pageable);

    Cliente atualizeClientePeloId(Long id, ClienteDTO cliente);

    Cliente busqueClientePeloId(Long id);

    Cliente busqueClientePeloCPF(String cpf);

    Cliente busqueClientePelaCNH(String cnh);

    Cliente busqueClientePeloEmail(String email);

    Page<ClienteResponseDTO> busqueClientePeloNome(String name, Pageable pageable);

    Cliente salveNovoCliente(ClienteDTO cliente);

    Cliente retorneSeExistirEnderecoParaClienteComId(Long id);

    void deleteClientePeloId(Long id);

    void salveEnderecoCliente(Cliente cliente, Endereco endereco);
}