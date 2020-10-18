package io.github.monthalcantara.nossobancodigital.service;

import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteServiceTest {

    Cliente cliente, clienteSalvo;

    @Autowired
    ClienteService clienteService;

    @MockBean
    Pageable pageable;

    @Test
    @DisplayName("Deve criar um novo cliente")
    public void criaNovoClienteTest() {
        cliente = clienteService.salveNovoCliente(geradorDeCliente());
        Assertions.assertNotNull(cliente);
    }

    private ClienteDTO geradorDeCliente() {
        return ClienteDTO.builder()
                .cnh("14797853560")
                .cpf("81732968047")
                .email("emailTest@gmail.com")
                .dataDeNascimento(LocalDate.of(1990,10,20))
                .nome("Cliente")
                .sobrenome(" Teste")
                .build();
    }
}
