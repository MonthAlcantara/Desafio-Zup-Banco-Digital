package io.github.monthalcantara.nossobancodigital.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Conta;
import io.github.monthalcantara.nossobancodigital.validation.annotations.MaiorIdade;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCNH;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoCPF;
import io.github.monthalcantara.nossobancodigital.validation.annotations.UnicoEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    @NotBlank(message = " {campo.nome.obrigatorio}")
    private String nome;

   @NotBlank(message = "{campo.sobrenome.obrigatorio}")
    private String sobrenome;

    @NotBlank(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    @UnicoCPF(message = "{campo.cpf.repetido}")
    private String cpf;

    @NotBlank(message = "{campo.email.obrigatorio}")
    @Email(message = "{campo.email.invalido}")
    @UnicoEmail(message = "{campo.email.repetido}")
    private String email;

    @NotBlank(message = "{campo.cnh.obrigatorio}")
    @UnicoCNH(message = "{campo.cnh.repetido}")
    @Size(max = 11, min = 11, message = "{campo.cnh.ìnvalido}")
    private String cnh;

    @NotNull(message = "{campo.data-nascimento.obrigatorio}")
    @MaiorIdade(message = "{campo.data-nascimento.maioridade}")
    @Past(message = "{campo.data-nascimento.invalida}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    public Cliente converteParaCliente(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .nome(clienteDTO.getNome())
                .sobrenome(clienteDTO.getSobrenome())
                .cpf(clienteDTO.getCpf())
                .email(clienteDTO.getEmail())
                .cnh(clienteDTO.getCnh())
                .dataDeNascimento(clienteDTO.getDataDeNascimento())
                .build();
    }

    public boolean verificaTodosOsDadosEstaoCompletos(){
        return this.nome != null && !(this.sobrenome.isEmpty())
                && this.sobrenome != null && !(this.sobrenome.isEmpty())
                && this.email != null && !(this.email.isEmpty())
                && this.cnh != null&& !(this.cnh.isEmpty())
                && this.dataDeNascimento != null;
    }
}



