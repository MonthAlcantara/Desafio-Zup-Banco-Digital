package io.github.monthalcantara.nossobancodigital.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private LocalDate dataDeNascimento;

}
