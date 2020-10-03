package io.github.monthalcantara.nossobancodigital.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EnderecoResponseDTO implements Serializable {


    private String cep;

    private String rua;

    private String bairro;

    private String complemento;

    private String cidade;

    private String estado;
}
