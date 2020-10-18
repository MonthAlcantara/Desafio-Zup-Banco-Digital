package io.github.monthalcantara.nossobancodigital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Conta {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 4)
    private String agencia;

    @Column(length = 8)
    private String conta;

    private String CodigoBanco = "191";

    @OneToOne
    private Cliente cliente;
}
