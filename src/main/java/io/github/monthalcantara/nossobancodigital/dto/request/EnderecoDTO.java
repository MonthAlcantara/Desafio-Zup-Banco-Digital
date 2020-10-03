package io.github.monthalcantara.nossobancodigital.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO implements Serializable {

    @NotEmpty
    private String cep;

    @NotEmpty
    private String rua;

    @NotEmpty
    private String bairro;

    @NotEmpty
    private String complemento;

    @NotEmpty
    private String cidade;

    @NotEmpty
    private String estado;

}
