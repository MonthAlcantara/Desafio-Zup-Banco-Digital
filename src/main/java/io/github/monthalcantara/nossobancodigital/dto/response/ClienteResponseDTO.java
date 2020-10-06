package io.github.monthalcantara.nossobancodigital.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.monthalcantara.nossobancodigital.model.DocumentoCliente;
import io.github.monthalcantara.nossobancodigital.model.Endereco;
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

    private LocalDate dataDeNascimento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Endereco endereco;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentoCliente documentoCliente;
}
