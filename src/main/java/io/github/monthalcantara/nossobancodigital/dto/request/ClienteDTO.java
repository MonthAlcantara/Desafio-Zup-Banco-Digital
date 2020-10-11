package io.github.monthalcantara.nossobancodigital.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import io.github.monthalcantara.nossobancodigital.model.Conta;
import io.github.monthalcantara.nossobancodigital.validation.annotations.MaiorIdade;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCNH;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCPF;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    @NotEmpty(message = " {campo.nome.obrigatorio}")
    private String nome;

    @NotEmpty(message = "{campo.sobrenome.obrigatorio}")
    private String sobrenome;

    @CPF(message = "{campo.cpf.invalido}")
    @UnicoCPF(message = "{campo.cpf.repetido}")
    private String cpf;

    @Email(message = "{campo.email.invalido}")
    @UnicoEmail(message = "{campo.email.repetido}")
    private String email;

    @NotEmpty(message = "{campo.cnh.obrigatorio}")
    @UnicoCNH(message = "{campo.cnh.repetido}")
    @Size(max = 11, min = 11, message = "{campo.cnh.ìnvalido}")
    private String cnh;

    @NotNull(message = "{campo.data-nascimento.obrigatorio}")
    @MaiorIdade(message = "{campo.data-nascimento.maioridade}")
    @Past(message = "{campo.data-nascimento.invalida}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    private Conta conta;

}
