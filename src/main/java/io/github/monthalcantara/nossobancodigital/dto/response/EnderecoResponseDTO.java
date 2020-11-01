package io.github.monthalcantara.nossobancodigital.dto.response;

import io.github.monthalcantara.nossobancodigital.model.Endereco;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO implements Serializable {

    private String cep;

    private String rua;

    private String bairro;

    private String complemento;

    private String cidade;

    private String estado;

    public EnderecoResponseDTO(Endereco endereco) {
        this.bairro = endereco.getBairro();
        this.rua = endereco.getRua();
        this.cep = endereco.getCep();
        this.complemento = endereco.getComplemento();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();

    }
}
