package io.github.monthalcantara.nossobancodigital.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 20, nullable = false)
    private String nome;

    @NotBlank
    @Column(length = 100)
    private String sobrenome;

    @NotBlank
    @CPF
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(unique = true, nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return cpf.equals(cliente.cpf) &&
                email.equals(cliente.email) &&
                cnh.equals(cliente.cnh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, email, cnh);
    }

    public boolean verificaDadosCompletosPassoUm() {
        return this.nome != null && !(this.sobrenome.isEmpty())
                && this.sobrenome != null && !(this.sobrenome.isEmpty())
                && this.email != null && !(this.email.isEmpty())
                && this.cnh != null && !(this.cnh.isEmpty())
                && this.dataDeNascimento != null;
    }

    public boolean verificaDadosCompletosPassoDois() {
        return this.verificaDadosCompletosPassoUm()
                && this.endereco.verificaTodosCamposEstaoCompletos();
    }
}