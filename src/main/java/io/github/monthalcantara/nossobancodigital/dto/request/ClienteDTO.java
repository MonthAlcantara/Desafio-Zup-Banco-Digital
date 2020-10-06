package io.github.monthalcantara.nossobancodigital.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCNH;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCPF;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoEmail;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    @NotEmpty(message = " O nome do Cliente é obrigatório")
    private String nome;

    @NotEmpty(message = " O Sobrenome do Cliente é obrigatório")
    private String sobrenome;

    @CPF(message = " Por favor digite um CPF válido")
    @UnicoCPF(message = "Já existe um cadastro de cliente com esse CPF")
    private String cpf;

    @Email(message = " Por favor digite um Email válido")
    @UnicoEmail(message = "Já existe um cadastro de cliente com esse Email")
    private String email;

    @NotEmpty(message = " A CNH do Cliente é obrigatória")
    @UnicoCNH(message = "Já existe um cadastro de cliente com esse CNH")
    @Size(max = 11, min = 11, message = "Digite um CNH válido")
    private String cnh;

   //@Past(message = "A data informada deve ser menor que a data atual")
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dataDeNascimento;

}
