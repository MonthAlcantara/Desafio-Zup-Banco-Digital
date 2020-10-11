package io.github.monthalcantara.nossobancodigital.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO implements Serializable {

    @NotEmpty(message = "{campo.cep.obrigatorio}")
    private String cep;

    @NotEmpty(message = "{campo.rua.obrigatorio}")
    private String rua;

    @NotEmpty(message = "{campo.bairro.obrigatorio}")
    private String bairro;

    @NotEmpty(message = "{campo.complemento.obrigatorio}")
    private String complemento;

    @NotEmpty(message = "{campo.cidade.obrigatorio}")
    private String cidade;

    @NotEmpty(message = "{campo.estado.obrigatorio}")
    private String estado;

}
