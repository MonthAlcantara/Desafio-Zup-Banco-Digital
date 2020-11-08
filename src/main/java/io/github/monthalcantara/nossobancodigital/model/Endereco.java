package io.github.monthalcantara.nossobancodigital.model;

import io.github.monthalcantara.nossobancodigital.dto.request.EnderecoDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue
    private Long id;

    private String cep;

    private String rua;

    private String bairro;

    private String complemento;

    private String cidade;

    private String estado;

    public Endereco(EnderecoDTO enderecoDTO) {
        this.cep = enderecoDTO.getCep();
        this.rua = enderecoDTO.getRua();
        this.bairro = enderecoDTO.getBairro();
        this.complemento = enderecoDTO.getComplemento();
        this.cidade = enderecoDTO.getCidade();
        this.estado = enderecoDTO.getEstado();

    }

    public boolean verificaTodosCamposEstaoCompletos() {
        return this.cep != null && !(this.cep.isEmpty())
                && this.rua != null && !(this.rua.isEmpty())
                && this.bairro != null && !(this.bairro.isEmpty())
                && this.complemento != null && !(this.complemento.isEmpty())
                && this.cidade != null && !(this.cidade.isEmpty())
                && this.estado != null && !(this.estado.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(cep, endereco.cep) &&
                Objects.equals(bairro, endereco.bairro) &&
                Objects.equals(cidade, endereco.cidade) &&
                Objects.equals(estado, endereco.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, bairro, cidade, estado);
    }

    public Long getId() {
        if (this.id == null) {
            throw new RecursoNaoEncontradoException("Não existe um id atrelado a esse endereço");
        }
        return this.id;
    }
}