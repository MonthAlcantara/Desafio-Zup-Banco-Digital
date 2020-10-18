package io.github.monthalcantara.nossobancodigital.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidacaoConstraintTest {

    @MockBean
    ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar cliente sem nome")
    void violacaoCLienteSemNomeTest() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);

        boolean contains = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()).contains("O Nome do Cliente é obrigatório.");
        assertTrue(contains);
    }

    private ClienteDTO geradorDeClienteDTO() {
        return ClienteDTO.builder()
                .cnh("14797853560")
                .cpf("81732968047")
                .email("email@gmail.com")
                .dataDeNascimento(null)
                .nome("Cliente")
                .sobrenome(" Teste")
                .build();
    }

    private Cliente geradorDeCliente() {
        return Cliente.builder()
                .cnh("14797853560")
                .cpf("81732968047")
                .email("emailTest@gmail.com")
                .dataDeNascimento(LocalDate.parse("24/10/1990"))
                .nome("Cliente")
                .sobrenome(" Teste")
                .build();
    }

    private String geradorDeJson(Object o) throws Exception {
        return new ObjectMapper().writeValueAsString(o);
    }
}

