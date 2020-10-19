package io.github.monthalcantara.nossobancodigital.service;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
import io.github.monthalcantara.nossobancodigital.repository.EnderecoRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.service.interfaces.EnderecoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class EnderecoServiceTest {
    Endereco endereco, enderecoSalvo;

    Page<Endereco> paginaEnderecos;

    @Autowired
    EnderecoService enderecoService;

    @MockBean
    ClienteService clienteService;

    @MockBean
    EnderecoRepository enderecoRepository;

    @MockBean
    Pageable pageable;

    @Test
    @DisplayName("Deve buscar todos os endereços cadastrados")
    public void busqueTodosClientes() {
        BDDMockito.given(enderecoRepository.findAll(Mockito.any(Pageable.class))).willReturn(paginaEnderecos);
        Assertions.assertNotNull(enderecoService.busqueTodosClientes(pageable));
    }

    @Test
    @DisplayName("Deve retornar erro ao não encontrar endereco pelo id")
    public void retorneSeExistirEnderecoComIdExceptionTest() {
        BDDMockito.given(enderecoRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(endereco));
        RuntimeException runtimeException = Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> enderecoService.busqueEnderecoPeloId(1L));
        Assertions.assertTrue(runtimeException.getMessage().contains("Não foi encontrado endereco com esse id: 1"));
    }

    @Test
    @DisplayName("Deve retornar erro ao não encontrar endereco pelo cep")
    public void busqueEnderecoPeloCepExceptionTest() {
        BDDMockito.given(enderecoRepository.findByCep(Mockito.anyString())).willReturn(Optional.ofNullable(endereco));
        RuntimeException runtimeException = Assertions.assertThrows(RecursoNaoEncontradoException.class, () -> enderecoService.busqueEnderecoPeloCep("42000000"));
        Assertions.assertTrue(runtimeException.getMessage().contains("Não foi encontrado endereco com esse cep: 42000000"));
    }

    @Test
    @DisplayName("Deve criar um novo endereço")
    public void salveNovoEnderecoTest() {
        endereco = geradorDeEndereco();
        BDDMockito.given(clienteService.busqueClientePeloId(Mockito.anyLong())).willReturn(geradorDeCliente());
        BDDMockito.given(enderecoRepository.save(Mockito.any(Endereco.class))).willReturn(endereco);
        enderecoSalvo = enderecoService.salveNovoEndereco(1L, geradorDeEnderecoDTO());

        Assertions.assertNotNull(enderecoSalvo);

    }

    @Test
    @DisplayName("Deve buscar endereço pelo Id")
    public void busqueEnderecoPeloIdTest() {
        BDDMockito.given(enderecoRepository.findById(Mockito.anyLong())).willReturn(Optional.of(geradorDeEndereco()));
        endereco = enderecoService.busqueEnderecoPeloId(1L);
        Assertions.assertNotNull(endereco);
    }

    @Test
    @DisplayName("Deve buscar endereço pelo CEP")
    public void busqueEnderecoPeloCepTest() {
        BDDMockito.given(enderecoRepository.findByCep(Mockito.any(String.class))).willReturn(Optional.of(geradorDeEndereco()));
        endereco = enderecoService.busqueEnderecoPeloCep("42000000");
        Assertions.assertNotNull(endereco);
    }

    private EnderecoDTO geradorDeEnderecoDTO() {
        return EnderecoDTO.builder()
                .bairro("centro")
                .cep("42000000")
                .cidade("Cidade Teste")
                .complemento("57E")
                .estado("Bahia")
                .rua("Rua de teste")
                .build();
    }

    private Endereco geradorDeEndereco() {
        return Endereco.builder()
                .bairro("centro")
                .cep("42000000")
                .cidade("Cidade Teste")
                .complemento("57E")
                .estado("Bahia")
                .rua("Rua de teste")
                .build();
    }

    private Cliente geradorDeCliente() {
        return Cliente.builder()
                .cnh("14797853560")
                .cpf("81732968047")
                .email("emailTest@gmail.com")
                .dataDeNascimento(LocalDate.of(1990, 10, 20))
                .nome("Cliente")
                .sobrenome(" Teste")
                .build();
    }
}

