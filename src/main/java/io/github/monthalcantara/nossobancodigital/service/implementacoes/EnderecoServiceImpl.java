package io.github.monthalcantara.nossobancodigital.service.implementacoes;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.mappers.EnderecoMapper;
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

import java.util.List;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    EnderecoMapper enderecoMapper;

    @Override
    public Page<EnderecoResponseDTO> busqueTodosClientes(Pageable pageable) {
        Page<Endereco> paginaEnderecos = enderecoRepository.findAll(pageable);
        return converteParaPageEnderecoResponseDTO(paginaEnderecos, pageable);
    }

    @Override
    public Endereco salveNovoEndereco(Long id, EnderecoDTO enderecoDTO) {

        Cliente cliente = clienteService.busqueClientePeloId(id);

        Endereco endereco = converteParaEndereco(enderecoDTO);
        cliente.setEndereco(endereco);
        System.out.println(endereco);
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        clienteService.salveEnderecoCliente(cliente, enderecoSalvo);
        return (converteParaEndereco(enderecoDTO));
    }

    @Override
    public Endereco busqueEnderecoPeloId(Long id) {
        return retorneSeExistirEnderecoComId(id);
    }

    @Override
    public Endereco busqueEnderecoPeloCep(String cep) {
        return enderecoRepository.findByCep(cep)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Não foi encontrado endereco com esse cep: " + cep));
    }

    @Override
    public Endereco atualizeEnderecoSeExistir(Long id, EnderecoDTO enderecoDTO) {
        Endereco enderecoEncontrado = retorneSeExistirEnderecoComId(id);
        Endereco endereco = converteParaEndereco(enderecoDTO);
        enderecoEncontrado.setBairro(endereco.getBairro());
        enderecoEncontrado.setCep(endereco.getCep());
        enderecoEncontrado.setCidade(endereco.getCidade());
        enderecoEncontrado.setEstado(endereco.getEstado());
        enderecoEncontrado.setComplemento(endereco.getComplemento());
        enderecoEncontrado.setRua(endereco.getRua());
        endereco.setId(enderecoEncontrado.getId());
        return enderecoRepository.save(enderecoEncontrado);
    }

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

    private Endereco converteParaEndereco(EnderecoDTO enderecoDTO) {
        return enderecoMapper.converteParaEndereco(enderecoDTO);
    }

    private List<EnderecoResponseDTO> converteParaListaEnderecoResponseDTO(List<Endereco> listaEndereco) {
        return enderecoMapper.converteParaListaEnderecoResponseDTO(listaEndereco);
    }

    private Page<EnderecoResponseDTO> converteParaPageEnderecoResponseDTO(Page<Endereco> paginaEnderecos, Pageable pageable) {
        List<EnderecoResponseDTO> dtos = converteParaListaEnderecoResponseDTO(paginaEnderecos.getContent());
        return new PageImpl<>(dtos, pageable, paginaEnderecos.getTotalElements());
    }

}