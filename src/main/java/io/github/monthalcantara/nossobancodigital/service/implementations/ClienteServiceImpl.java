package io.github.monthalcantara.nossobancodigital.service.implementations;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.mappers.ClienteMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteMapper clienteMapper;

    @Override
    public Page<ClienteResponseDTO> busqueTodosClientes(Pageable pageable) {
        Page<Cliente> paginaClientes = clienteRepository.findAll(pageable);
        return converteParaPageClienteResponseDTO(paginaClientes, pageable);
    }

    @Override
    public Page<ClienteResponseDTO> busqueClientePeloNome(@NotBlank String nome, Pageable pageable) {
        Assert.hasText(nome, "O campo nome não pode ser vazio");
        Page<Cliente> paginaClientes = clienteRepository.findAllByNome(nome, pageable).get();
        return converteParaPageClienteResponseDTO(paginaClientes, pageable);
    }

    @Override
    public Cliente busqueClientePeloId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        return clienteOptional.orElseThrow(() -> new RecursoNaoEncontradoException("Não existe cliente cadastrado com o ID: " + id));
    }

    @Override
    public Cliente busqueClientePeloCPF(@NotBlank String cpf) {
        Assert.hasText(cpf, "CPF não pode estar vazio");
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        return clienteOptional.orElseThrow(() -> new RecursoNaoEncontradoException("Não existe cliente cadastrado com o CPF: " + cpf));
    }

    @Override
    public Cliente busqueClientePelaCNH(@NotBlank String cnh) {
        Assert.hasText(cnh, "CNH não pode estar vazio");
        Optional<Cliente> clienteOptional = clienteRepository.findByCnh(cnh);
        return clienteOptional.orElseThrow(() -> new RecursoNaoEncontradoException("Não existe cliente cadastrado com a CNH: " + cnh));
    }

    @Override
    public Cliente busqueClientePeloEmail(@NotBlank String email) {
        Assert.hasText(email, "Email não pode estar vazio");
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);
        return clienteOptional.orElseThrow(() -> new RecursoNaoEncontradoException("Não existe cliente cadastrado com o E-mail: " + email));
    }

    @Transactional
    @Override
    public Cliente atualizeClientePeloId(Long id, @NotNull ClienteDTO cliente) {
        Assert.isTrue(cliente.verificaTodosOsDadosEstaoCompletos(), "Todos os dados do cliente devem ser informados");
        busqueClientePeloId(id);
        Cliente clienteAtualizado = cliente.converteParaCliente(cliente);
        clienteAtualizado.setId(id);

        return clienteRepository.save(clienteAtualizado);
    }

    @Transactional
    @Override
    public Cliente salveNovoCliente(ClienteDTO clienteDTO) {
        Assert.isTrue(clienteDTO.verificaTodosOsDadosEstaoCompletos(), "Todos os dados do cliente devem ser informados");
        Cliente cliente = converteParaCliente(clienteDTO);
        return clienteRepository.save(cliente);
    }

    @Transactional
    @Override
    public void deleteClientePeloId(Long id) {
        Cliente clienteEncontrado = busqueClientePeloId(id);
        clienteRepository.delete(clienteEncontrado);
    }

    @Transactional
    @Override
    public void salveEnderecoCliente(@NotNull Cliente cliente, @NotNull Endereco endereco) {
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente retorneSeExistirEnderecoParaClienteComId(Long id) {
        Cliente clienteEncontrado = busqueClientePeloId(id);
        Optional<Cliente> enderecoOptional = clienteRepository.findByEndereco(clienteEncontrado.getEndereco());
        return enderecoOptional.orElseThrow(() -> new RecursoNaoEncontradoException("O cliente de ID: " + id + " não possui endereço cadastrado"));
    }

    private Cliente converteParaCliente(ClienteDTO cliente) {
        return clienteMapper.converteParaCliente(cliente);
    }

    private List<ClienteResponseDTO> converteParaListaClienteResponseDTO(List<Cliente> listaClientes) {
        return clienteMapper.converteParaListaClienteResponseDTO(listaClientes);
    }

    private Page<ClienteResponseDTO> converteParaPageClienteResponseDTO(Page<Cliente> paginaClientes, Pageable pageable) {
        List<ClienteResponseDTO> dtos = converteParaListaClienteResponseDTO(paginaClientes.getContent());
        return new PageImpl<>(dtos, pageable, paginaClientes.getTotalElements());
    }
}