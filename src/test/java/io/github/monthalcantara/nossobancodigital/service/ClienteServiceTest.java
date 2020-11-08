package io.github.monthalcantara.nossobancodigital.service;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ClienteServiceTest {
    Cliente cliente, clienteSalvo;

    @Autowired
    ClienteService clienteService;

    @MockBean
    ClienteRepository clienteRepository;

    @MockBean
    Page page;

    @MockBean
    Pageable pageable;

    @Test
    @DisplayName("Deve buscar todos os clientes")
    public void busqueTodosClientesTest() {
        Mockito.when(clienteRepository.findAll(pageable)).thenReturn(page);
        Assertions.assertNotNull(clienteService.busqueTodosClientes(pageable));
    }

    @Test
    @DisplayName("Deve buscar página de cliente pelo nome")
    public void buscaClientePeloNome() {
        Mockito.when(clienteRepository.findAllByNome("maria", pageable)).thenReturn(Optional.of(page));
        Assertions.assertNotNull(clienteService.busqueClientePeloNome("maria", pageable));
    }

    @Test
    @DisplayName("Deve lançar erro ao receber nome do cliente vazio pelo nome")
    public void buscaClientePeloNomeException01() {
        RuntimeException runtimeException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> clienteService.busqueClientePeloNome(" ", pageable));
        Assertions.assertEquals(runtimeException.getMessage(), "O campo nome não pode ser vazio");
    }

    @Test
    @DisplayName("Deve lançar erro ao não encontrar cliente vazio pelo nome")
    public void buscaClientePeloNomeException02() {
        Mockito.when(clienteRepository.findAllByNome("maria", pageable)).thenReturn(Optional.empty());
        RuntimeException runtimeException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> clienteService.busqueClientePeloNome(" ", pageable));
        Assertions.assertEquals(runtimeException.getMessage(), "O campo nome não pode ser vazio");
    }

    @Test
    @DisplayName("Deve buscar cliente pelo id")
    public void busqueClientePeloIdTest() {
        cliente = geradorDeCliente();
        BDDMockito.given(clienteRepository.findById(Mockito.any(Long.class))).willReturn(Optional.of(cliente));
        clienteSalvo = clienteService.busqueClientePeloId(1L);
        Assertions.assertNotNull(clienteSalvo);
    }

    @Test
    @DisplayName("Deve lançar erro ao não encontrar cliente pelo id")
    public void busqueClientePeloIdExceptionTest() {

        RuntimeException runtimeException = assertThrows(RecursoNaoEncontradoException.class, () ->
                clienteService.busqueClientePeloId(1L));

        assertEquals(runtimeException.getMessage(), "Não existe cliente cadastrado com o ID: 1");

    }

    @Test
    @DisplayName("Deve buscar cliente pelo CPF")
    public void busqueClientePeloCPFTest() {

        cliente = geradorDeCliente();
        BDDMockito.given(clienteRepository.findByCpf(Mockito.any(String.class))).willReturn(Optional.of(cliente));
        clienteSalvo = clienteService.busqueClientePeloCPF("81732968047");
        Assertions.assertNotNull(clienteSalvo);
    }

    @Test
    @DisplayName("Deve lançar erro ao não encontrar cliente pelo cpf")
    public void busqueClientePeloCPFExceptionTest() {

        RuntimeException runtimeException = assertThrows(RecursoNaoEncontradoException.class, () ->
                clienteService.busqueClientePeloCPF("81732968047"));


        assertEquals(runtimeException.getMessage(), "Não existe cliente cadastrado com o CPF: 81732968047");

    }

    @Test
    @DisplayName("Deve buscar cliente pela CNH")
    public void busqueClientePelaCNHTest() {
        cliente = geradorDeCliente();
        BDDMockito.given(clienteRepository.findByCnh(Mockito.any(String.class))).willReturn(Optional.of(cliente));
        clienteSalvo = clienteService.busqueClientePelaCNH("14797853560");
        Assertions.assertNotNull(clienteSalvo);
    }

    @Test
    @DisplayName("Deve lançar erro ao não encontrar cliente pela cnh")
    public void busqueClientePeloCNHExceptionTest() {

        RuntimeException runtimeException = assertThrows(RecursoNaoEncontradoException.class, () ->
                clienteService.busqueClientePelaCNH("81732968047"));


        assertEquals(runtimeException.getMessage(), "Não existe cliente cadastrado com a CNH: 81732968047");

    }

    @Test
    @DisplayName("Deve buscar cliente pelo Email")
    public void busqueClientePeloEmailTest() {
        cliente = geradorDeCliente();
        BDDMockito.given(clienteRepository.findByEmail(Mockito.any(String.class))).willReturn(Optional.of(cliente));
        clienteSalvo = clienteService.busqueClientePeloEmail("emailTest@gmail.com");
        Assertions.assertNotNull(clienteSalvo);
    }


    @Test
    @DisplayName("Deve criar um novo cliente")
    public void salveNovoClienteTest() {
        cliente = geradorDeCliente();
        BDDMockito.given(clienteRepository.save(Mockito.any(Cliente.class))).willReturn(cliente);
        cliente = clienteService.salveNovoCliente(geradorDeClienteDTO());
        Assertions.assertNotNull(cliente);
    }

    @Test
    @DisplayName("Deve deletar cliente pelo id")
    public void deleteClientePeloIdTest() {
        RuntimeException runtimeException = assertThrows(RecursoNaoEncontradoException.class, () ->
                clienteService.deleteClientePeloId(1L));

        assertEquals(runtimeException.getMessage(), "Não existe cliente cadastrado com o ID: 1");
    }


    private ClienteDTO geradorDeClienteDTO() {
        return ClienteDTO.builder()
                .cnh("14797853560")
                .cpf("81732968047")
                .email("emailTest@gmail.com")
                .dataDeNascimento(LocalDate.of(1990, 10, 20))
                .nome("Cliente")
                .sobrenome(" Teste")
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

