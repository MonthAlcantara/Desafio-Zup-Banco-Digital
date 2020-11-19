package io.github.monthalcantara.nossobancodigital.model;

import io.github.monthalcantara.nossobancodigital.dto.response.ClienteResponseDTO;
import io.github.monthalcantara.nossobancodigital.dto.response.EnderecoResponseDTO;
import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;


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

    @Deprecated
    public Cliente() {
    }

    public Cliente(@NotBlank String nome,
                   @NotBlank String sobrenome,
                   @NotBlank @CPF String cpf,
                   @NotBlank @Email String email,
                   @NotBlank String cnh,
                   @NotNull @Past LocalDate dataDeNascimento) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.cnh = cnh;
        this.dataDeNascimento = dataDeNascimento;
    }

    public ClienteResponseDTO paraResponse(){
        EnderecoResponseDTO enderecoResponseDTO;
        if(this.endereco == null){
            enderecoResponseDTO = null;
        }else{
        enderecoResponseDTO = this.endereco.converteParaResponse();

        }
        return new ClienteResponseDTO(this.id,
                this.nome
                ,this.sobrenome,
                this.cpf,
                this.email,
                this.cnh,
                this.dataDeNascimento,
                enderecoResponseDTO,
                this.documentoCliente,
                this.conta);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public DocumentoCliente getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(DocumentoCliente documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

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