package io.github.monthalcantara.nossobancodigital.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.monthalcantara.nossobancodigital.model.Cliente;
import io.github.monthalcantara.nossobancodigital.model.Conta;
import io.github.monthalcantara.nossobancodigital.model.DocumentoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

public class ClienteResponseDTO implements Serializable {

    private Long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String email;

    private String cnh;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EnderecoResponseDTO endereco;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentoCliente documentoCliente;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conta conta;

    @Deprecated
    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Long id,
                              String nome,
                              String sobrenome,
                              String cpf,
                              String email,
                              String cnh,
                              LocalDate dataDeNascimento,
                              EnderecoResponseDTO endereco,
                              DocumentoCliente documentoCliente,
                              Conta conta) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.cnh = cnh;
        this.dataDeNascimento = dataDeNascimento;
        this.endereco = endereco;
        this.documentoCliente = documentoCliente;
        this.conta = conta;
    }

    public Long getId() {
        return id;
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

    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }

    public DocumentoCliente getDocumentoCliente() {
        return documentoCliente;
    }

    public Conta getConta() {
        return conta;
    }
}
