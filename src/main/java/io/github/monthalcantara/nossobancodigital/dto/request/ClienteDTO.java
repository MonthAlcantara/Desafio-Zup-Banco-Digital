package io.github.monthalcantara.nossobancodigital.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.validation.annotations.MaiorIdade;
import io.github.monthalcantara.nossobancodigital.validation.annotations.ValorUnico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

public class ClienteDTO implements Serializable {

    @NotBlank(message = " {campo.nome.obrigatorio}")
    private String nome;

    @NotBlank(message = "{campo.sobrenome.obrigatorio}")
    private String sobrenome;

    @NotBlank(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    @ValorUnico(domainClass = Cliente.class, fieldName = "cpf", message = "{campo.cpf.repetido}")
    private String cpf;

    @NotBlank(message = "{campo.email.obrigatorio}")
    @Email(message = "{campo.email.invalido}")
    @ValorUnico(domainClass = Cliente.class, fieldName = "email", message = "{campo.email.repetido}")
    private String email;

    @NotBlank(message = "{campo.cnh.obrigatorio}")
    @ValorUnico(domainClass = Cliente.class, fieldName = "cnh", message = "{campo.cnh.repetido}")
    @Size(max = 11, min = 11, message = "{campo.cnh.ìnvalido}")
    private String cnh;

    @NotNull(message = "{campo.data-nascimento.obrigatorio}")
    @MaiorIdade(message = "{campo.data-nascimento.maioridade}")
    @Past(message = "{campo.data-nascimento.invalida}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    @Deprecated
    public ClienteDTO() {
    }

    public ClienteDTO(@NotBlank String nome,
                      @NotBlank String sobrenome,
                      @NotBlank @CPF String cpf,
                      @NotBlank @Email String email,
                      @NotBlank @Size String cnh,
                      @NotNull @Past LocalDate dataDeNascimento) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.cnh = cnh;
        this.dataDeNascimento = dataDeNascimento;
    }

    public Cliente paraCliente() {
        return new Cliente(this.nome, this.sobrenome, this.cpf, this.email, this.cnh, this.dataDeNascimento);
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getCnh() {
        return cnh;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }


    public boolean verificaTodosOsDadosEstaoCompletos() {
        return this.nome != null && !(this.sobrenome.isEmpty())
                && this.sobrenome != null && !(this.sobrenome.isEmpty())
                && this.email != null && !(this.email.isEmpty())
                && this.cnh != null && !(this.cnh.isEmpty())
                && this.dataDeNascimento != null;
    }
}



