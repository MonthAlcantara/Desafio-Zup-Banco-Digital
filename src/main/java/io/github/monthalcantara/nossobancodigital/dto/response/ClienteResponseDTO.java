package io.github.monthalcantara.nossobancodigital.dto.response;

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

    private LocalDate DataDeNascimento;
}
