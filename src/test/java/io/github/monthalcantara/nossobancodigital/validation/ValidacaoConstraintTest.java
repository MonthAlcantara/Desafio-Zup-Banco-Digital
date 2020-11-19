package io.github.monthalcantara.nossobancodigital.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.monthalcantara.nossobancodigital.dto.request.ClienteDTO;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.validation.annotations.MaiorIdade;
import io.github.monthalcantara.nossobancodigital.validation.validators.MaiorIdadeValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.time.LocalDate;

public class ValidacaoConstraintTest {


    @DisplayName("Deve validar a data de Nascimento")
    @ParameterizedTest
    @CsvSource({"18,true", "17,false", "7,false", "35,true", "40,true", "19, true", "16,false"})
    void violacaoMaiorIdadeTest(int idadeTest, boolean resultadoEsperado) {
        MaiorIdade idade = Mockito.mock(MaiorIdade.class);
        Mockito.when(idade.maiorQue()).thenReturn(18);
        MaiorIdadeValidator idadeValidator = new MaiorIdadeValidator();
        idadeValidator.initialize(idade);

        LocalDate dezoitoAnos = LocalDate.now().minusYears(idadeTest);
        boolean valido = idadeValidator.isValid(dezoitoAnos, null);
        Assertions.assertEquals(resultadoEsperado, valido);
    }


    private ClienteDTO geradorDeClienteDTO() {
        LocalDate dataNascimento = LocalDate.of(1990, 10, 20);
        return new ClienteDTO("Cliente"," Teste", "81732968047", "emailTest@gmail.com","14797853560",dataNascimento);

    }

    private Cliente geradorDeCliente() {
        LocalDate dataNascimento = LocalDate.of(1990, 10, 20);
        return new Cliente("Cliente"," Teste", "81732968047", "emailTest@gmail.com","14797853560",dataNascimento);
    }

    private String geradorDeJson(Object o) throws Exception {
        return new ObjectMapper().writeValueAsString(o);
    }
}

