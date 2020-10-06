package io.github.monthalcantara.nossobancodigital.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO implements Serializable {

    @NotEmpty(message = " Obrigatório informar o CEP do Cliente")
    private String cep;

    @NotEmpty(message = " Obrigatório informar a Rua do Cliente")
    private String rua;

    @NotEmpty(message = " Obrigatório informar o Bairro do Cliente")
    private String bairro;

    @NotEmpty(message = " Por favor informe um complemento")
    private String complemento;

    @NotEmpty(message = " Obrigatório informar a Cidade do Cliente")
    private String cidade;

    @NotEmpty(message = " Obrigatório informar o estado do Cliente")
    private String estado;

}
