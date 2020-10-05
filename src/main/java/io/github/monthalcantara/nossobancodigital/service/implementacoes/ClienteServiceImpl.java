package io.github.monthalcantara.nossobancodigital.service.implementacoes;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.mappers.ClienteMapper;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    ClienteMapper clienteMapper;

    @Override
    public Page<ClienteResponseDTO> busqueTodosClientes(Pageable pageable) {
        Page<Cliente> paginaClientes = clienteRepository.findAll(pageable);
        return converteParaPageClienteResponseDTO(paginaClientes, pageable);
    }

    @Override
    public Page<ClienteResponseDTO> busqueClientePeloNome(String nome, Pageable pageable) {
        Page<Cliente> paginaClientes = clienteRepository.findAllByNome(nome, pageable);
        return converteParaPageClienteResponseDTO(paginaClientes, pageable);
    }

    @Override
    public Cliente busqueClientePeloId(Long id) {
        return retorneSeExistirClienteComId(id);
    }

    @Override
    public Cliente busqueClientePeloCPF(String cpf) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
        return clienteOptional.orElseThrow(() -> new RecursoNaoEncontradoException("Não existe cliente cadastrado com o cpf: " + cpf));
    }

    @Override
    public Cliente busqueClientePelaCNH(String cnh) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCnh(cnh);
        return clienteOptional.orElseThrow(() -> new RecursoNaoEncontradoException("Não existe cliente cadastrado com o cnh: " + cnh));
    }

    @Override
    public Cliente atualizeClientePeloId(Long id, ClienteDTO cliente) {
        Cliente clienteEncontrado = retorneSeExistirClienteComId(id);
        clienteEncontrado.setCnh(cliente.getCnh());
        clienteEncontrado.setCpf(cliente.getCpf());
        clienteEncontrado.setDataDeNascimento(cliente.getDataDeNascimento());
        clienteEncontrado.setEmail(cliente.getEmail());
        clienteEncontrado.setNome(cliente.getNome());
        clienteEncontrado.setSobrenome(cliente.getSobrenome());
        return clienteRepository.save(clienteEncontrado);
    }


    public Cliente salveNovoCliente(ClienteDTO clienteDTO) {
        Cliente cliente = converteParaCliente(clienteDTO);
        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteClientePeloId(Long id) {
        Cliente clienteEncontrado = retorneSeExistirClienteComId(id);
        clienteRepository.delete(clienteEncontrado);
    }

    @Override
    public void salveEnderecoCliente(Cliente cliente, Endereco endereco) {
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void salveDocumentosCliente(Cliente cliente, String docFrente, String docVerso) {
        cliente.setFotoDocumentoFrente(docFrente);
        cliente.setFotoDocumentoVerso(docVerso);
        clienteRepository.save(cliente);
    }

    @Override
    public List<String> salveArquivosDocumentoCliente(String diretorio, Long id, MultipartFile arquivoFrente, MultipartFile arquivoVerso) {

        Cliente cliente = busqueClientePeloId(id);

        retorneSeExistirEnderecoParaClienteComId(id);

        List<String> arquivos = new ArrayList<>();
        Path diretorioPath = Paths.get(diretorio, "cliente_" + id);

        String nomeArquivoFrente = arquivoFrente.getOriginalFilename();
        String nomeArquivoVerso = arquivoVerso.getOriginalFilename();

        Path arquivoPathFrente = diretorioPath.resolve(nomeArquivoFrente);
        Path arquivoPathVerso = diretorioPath.resolve(nomeArquivoVerso);
        try {

            Files.createDirectories(diretorioPath);
            arquivoFrente.transferTo(arquivoPathFrente.toFile());
            arquivoVerso.transferTo(arquivoPathVerso.toFile());
            salveDocumentosCliente(cliente, nomeArquivoFrente, nomeArquivoVerso);

        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo: ", e);
        }
        arquivos.add(diretorioPath.toString() + "/" + nomeArquivoFrente);
        arquivos.add(diretorioPath.toString() + "/" + nomeArquivoVerso);
        arquivos.forEach(System.out::println);
        return arquivos;
    }

    public String busqueLocalizacaoDocumento(String diretorio, Long id, String nomeDoArquivo){
        Path diretorioPath = Paths.get(diretorio, "cliente_" + id);
        return  diretorioPath.toString() + "/" + nomeDoArquivo;
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

    private Cliente retorneSeExistirClienteComId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        return clienteOptional.orElseThrow(() -> new RecursoNaoEncontradoException("Não existe cliente cadastrado com o id: " + id));
    }

    //Atualizar
    private Endereco retorneSeExistirEnderecoParaClienteComId(Long id) {
        Cliente cliente = retorneSeExistirClienteComId(id);
        Optional<Endereco> enderecoOptional = clienteRepository.findEnderecoIdById(id);
        return enderecoOptional.orElseThrow(() -> new RecursoNaoEncontradoException("O cliente de id " + id + "Não possui endereço cadastrado"));
    }

}