package io.github.monthalcantara.nossobancodigital.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCNH;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCPF;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    @NotEmpty(message = " O nome do Cliente é obrigatório")
    private String nome;

    @NotEmpty(message = " O Sobrenome do Cliente é obrigatório")
    private String sobrenome;

    @CPF
    @UnicoCPF(message = "Já existe um cadastro de cliente com esse CPF")
    private String cpf;

    @Email
    @UnicoEmail(message = "Já existe um cadastro de cliente com esse Email")
    private String email;

    @NotEmpty(message = " A CNH do Cliente é obrigatória")
    @UnicoCNH(message = "Já existe um cadastro de cliente com esse CNH")
    @Size(max = 11, min = 11, message = "Digite um CNH válido")
    private String cnh;

//    @Past(message = "A data informada deve ser menor que a data atual")
@DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Temporal(TemporalType.DATE)
    private LocalDate dataDeNascimento;

}
