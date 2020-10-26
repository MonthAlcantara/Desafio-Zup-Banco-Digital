package io.github.monthalcantara.nossobancodigital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conta {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 4)
    private String agencia;

    @Column(length = 8)
    private String numeroConta;

    private String CodigoBanco = "191";

    @OneToOne(mappedBy = "conta")
    private Cliente cliente;
}
