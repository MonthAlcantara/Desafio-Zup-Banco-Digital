package io.github.monthalcantara.nossobancodigital.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class DocumentoCliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentoFrente;

    private String documentoVerso;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "documentoCliente", fetch = FetchType.LAZY)
    private Cliente cliente;
}
