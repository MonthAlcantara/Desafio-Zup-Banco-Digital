package io.github.monthalcantara.nossobancodigital.service.implementacoes;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.mappers.ClienteMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteMapper clienteMapper;

    @Override
    public Page<Cliente> busqueTodosClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable);

    }

    @Override
    public ClienteResponseDTO atualizeClientePeloId(Long id, ClienteDTO cliente) {
        return null;
    }

    @Override
    public ClienteResponseDTO busqueClientePeloId(Long id) {
        return null;
    }

    @Override
    public ClienteResponseDTO busqueClientePeloCPF(String cpf) {
        return null;
    }

    @Override
    public ClienteResponseDTO busqueClientePelaCNH(String cnh) {
        return null;
    }

    @Override
    public Page<ClienteResponseDTO> busqueClientePeloNome(String name, Pageable pageable) {
        return null;
    }

    @Override
    public ClienteResponseDTO salve(ClienteDTO cliente) {
        return null;
    }

    @Override
    public void deleteClientePeloId(Integer id) {

    }
}
