package io.github.monthalcantara.nossobancodigital.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.monthalcantara.nossobancodigital.model.Conta;
import io.github.monthalcantara.nossobancodigital.model.DocumentoCliente;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class ClienteResponseDTO implements Serializable {

    private Long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String email;

    private String cnh;
//
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private String dataDeNascimento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EnderecoResponseDTO endereco;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentoCliente documentoCliente;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conta conta;
}
