package io.github.monthalcantara.nossobancodigital.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 20, nullable = false)
    private String nome;

    @NotEmpty
    @Column(length = 100)
    private String sobrenome;

    @CPF
    @Column(nullable = false)
    private String cpf;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cnh;

    @NotNull
    @Past
    private LocalDate dataDeNascimento;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "documento_id")
    private DocumentoCliente documentoCliente;

    @OneToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
}