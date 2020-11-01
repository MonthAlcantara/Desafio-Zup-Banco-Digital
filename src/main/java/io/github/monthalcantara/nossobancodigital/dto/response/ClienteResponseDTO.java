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

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public ClienteResponseDTO(Cliente cliente) {

        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.sobrenome = cliente.getSobrenome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.cnh = cliente.getCnh();
        this.dataDeNascimento = cliente.getDataDeNascimento();
        this.documentoCliente = cliente.getDocumentoCliente();
        if (cliente.getEndereco() != null) {
            this.endereco = new EnderecoResponseDTO(cliente.getEndereco());
        }
        this.conta = cliente.getConta();
    }
}
