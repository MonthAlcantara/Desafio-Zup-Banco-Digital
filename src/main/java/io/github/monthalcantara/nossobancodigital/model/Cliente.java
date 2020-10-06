package io.github.monthalcantara.nossobancodigital.model;

import io.github.monthalcantara.nossobancodigital.exception.ViolacaoRegraNegocioException;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = " O nome do Cliente é obrigatório")
    @Column(length = 20, nullable = false)
    private String nome;

    @NotEmpty(message = " O Sobrenome do Cliente é obrigatório")
    @Column(length = 100)
    private String sobrenome;

    @CPF(message = " CPF inválido")
    @Column(nullable = false)
    private String cpf;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cnh;

    @Past(message = "A data informada deve ser menor que a data atual")
    private LocalDate dataDeNascimento;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "documento_id")
    private DocumentoCliente documentoCliente;


    public String getDataDeNascimento() {
        return dataDeNascimento.toString();
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataDeNascimento, formatter);
        LocalDate agora = LocalDate.now();
        if(Period.between(data, agora).isNegative() || Period.between(data, agora).equals(agora) ){
            throw new ViolacaoRegraNegocioException("Escolha uma data no passado");
        }else if (Period.between(data, agora).toTotalMonths() >= 216) {
            this.dataDeNascimento = data;
        } else {
            System.out.println(Period.between(data, agora).toTotalMonths());
            throw new ViolacaoRegraNegocioException("Você precisa ser maior de idade para abrir conta no nosso banco");
        }
    }
}