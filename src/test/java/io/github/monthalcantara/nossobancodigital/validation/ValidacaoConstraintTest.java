package io.github.monthalcantara.nossobancodigital.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.repository.ClienteRepository;
import io.github.monthalcantara.nossobancodigital.service.interfaces.ClienteService;
import io.github.monthalcantara.nossobancodigital.validation.annotations.MaiorIdade;
import io.github.monthalcantara.nossobancodigital.validation.validators.MaiorIdadeValidator;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @DisplayName("Deve validar a data de Nascimento")
    @ParameterizedTest
    @CsvSource({"18,true", "17,false", "7,false", "35,true", "40,true", "19, true", "16,false"})
    void ciolacaoMaiorIdadeTest(int idadeTest, boolean resultadoEsperado){
        MaiorIdade idade = Mockito.mock(MaiorIdade.class);
        Mockito.when(idade.maiorQue()).thenReturn(18);
        MaiorIdadeValidator idadeValidator = new MaiorIdadeValidator();
        idadeValidator.initialize(idade);

        LocalDate dezoitoAnos = LocalDate.now().minusYears(idadeTest);
        boolean valido = idadeValidator.isValid(dezoitoAnos, Mockito.mock(ConstraintValidatorContext.class));
        Assertions.assertEquals(resultadoEsperado, valido);
    }


//    @Test
//    @DisplayName("Deve lançar erro ao tentar cadastrar cliente sem nome")
//    void violacaoCLienteSemNomeTest() {
//        ClienteDTO clienteDTO = new ClienteDTO();
//        clienteDTO.setNome("");
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<ClienteDTO>> violations = validator.validate(clienteDTO);
//
//        boolean contains = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()).contains("O Nome do Cliente é obrigatório.");
//        assertTrue(contains);
//    }

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

