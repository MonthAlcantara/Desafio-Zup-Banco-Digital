package io.github.monthalcantara.nossobancodigital.service.implementations;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.repository.EnderecoRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
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

@Service
public class EnderecoServiceImpl implements EnderecoService {


    EnderecoRepository enderecoRepository;

    ClienteService clienteService;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, ClienteService clienteService) {
        this.enderecoRepository = enderecoRepository;
        this.clienteService = clienteService;
    }

    @Override
    public Page<EnderecoResponseDTO> busqueTodosClientes(Pageable pageable) {
        Page<Endereco> paginaEnderecos = enderecoRepository.findAll(pageable);
        return converteParaPageEnderecoResponseDTO(paginaEnderecos, pageable);
    }

    @Transactional
    @Override
    public Endereco salveNovoEndereco(Long id, @NotNull EnderecoDTO enderecoDTO) {

        Assert.isTrue(enderecoDTO.verificaTodosCamposEstaoCompletos(), "Todos os dados do endereço devem estar completos");
        Cliente cliente = clienteService.busqueClientePeloId(id);

        Endereco endereco = enderecoDTO.converteParaEndereco();
        Assert.isTrue(endereco.verificaTodosCamposEstaoCompletos(), "Todos os dados do endereço devem estar completos");
        cliente.setEndereco(endereco);
        Assert.isTrue(cliente.verificaDadosCompletosPassoDois(), "Todos os dados do cliente e endereço devem estar completos");
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        clienteService.salveEnderecoCliente(cliente, enderecoSalvo);
        return enderecoDTO.converteParaEndereco();
    }

    @Override
    public Endereco busqueEnderecoPeloId(Long id) {
        return retorneSeExistirEnderecoComId(id);
    }

    @Override
    public Endereco busqueEnderecoPeloCep(@NotBlank String cep) {
        Assert.hasText(cep, "O cep do endereço pprecisa ser informado");
        return enderecoRepository.findByCep(cep)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi encontrado endereco com esse cep: " + cep));
    }

    @Transactional
    @Override
    public Endereco atualizeEnderecoSeExistir(Long id, @NotNull EnderecoDTO enderecoDTO) {
        Assert.isTrue(enderecoDTO.verificaTodosCamposEstaoCompletos(), "Todos os campos do endereço devem ser informados");
        retorneSeExistirEnderecoComId(id);
        Endereco endereco = enderecoDTO.converteParaEndereco();
        endereco.setId(id);
        return enderecoRepository.save(endereco);
    }

    @Transactional
    @Override
    public void deleteEnderecoPeloId(Long id) {
        Endereco enderecoEncontrado = retorneSeExistirEnderecoComId(id);
        enderecoRepository.delete(enderecoEncontrado);
    }

    @Override
    public Endereco retorneSeExistirEnderecoComId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi encontrado endereco com esse id: " + id));
    }

    private List<EnderecoResponseDTO> converteParaListaEnderecoResponseDTO(List<Endereco> listaEndereco) {
        return null;//enderecoMapper.converteParaListaEnderecoResponseDTO(listaEndereco);
    }

    private Page<EnderecoResponseDTO> converteParaPageEnderecoResponseDTO(Page<Endereco> paginaEnderecos, Pageable pageable) {
        List<EnderecoResponseDTO> dtos = converteParaListaEnderecoResponseDTO(paginaEnderecos.getContent());
        return new PageImpl<>(dtos, pageable, paginaEnderecos.getTotalElements());
    }

}