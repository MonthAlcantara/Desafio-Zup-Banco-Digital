package io.github.monthalcantara.nossobancodigital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco implements Serializable {

    @Id
    private Long clienteId;

    private String cep;

    private String rua;

    private String bairro;

    private String complemento;

    private String cidade;

    private String estado;

}
