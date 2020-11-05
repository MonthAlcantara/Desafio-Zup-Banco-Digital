package io.github.monthalcantara.nossobancodigital.dto.request;

import io.github.monthalcantara.nossobancodigital.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
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

    public Endereco converteParaEndereco(EnderecoDTO endereco){
        return Endereco.builder()
                .bairro(endereco.getBairro())
                .rua(endereco.getRua())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .build();
    }

    public boolean verificaTodosCamposEstaoCompletos() {
        return this.cep != null && !(this.cep.isEmpty())
                && this.rua != null && !(this.rua.isEmpty())
                && this.bairro != null && !(this.bairro.isEmpty())
                && this.complemento != null && !(this.complemento.isEmpty())
                && this.cidade != null && !(this.cidade.isEmpty())
                && this.estado != null && !(this.estado.isEmpty());
    }
}

