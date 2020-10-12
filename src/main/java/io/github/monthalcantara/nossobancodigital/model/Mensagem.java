package io.github.monthalcantara.nossobancodigital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mensagem {

    private String remetente;

    private List<String> destinatarios;

    private String assunto;

    private String corpo;
}
