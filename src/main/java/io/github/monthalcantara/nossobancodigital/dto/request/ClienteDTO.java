package io.github.monthalcantara.nossobancodigital.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class ClienteDTO implements Serializable {

    @NotEmpty(message = " O nome do Cliente é obrigatório")
    private String nome;
    @NotEmpty(message = " O Sobrenome do Cliente é obrigatório")
    private String sobrenome;
    @CPF
    private String cpf;
    @Email
    private String email;
    @NotEmpty(message = " A CNH do Cliente é obrigatória")
    private String cnh;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate DataDeNascimento;
}
