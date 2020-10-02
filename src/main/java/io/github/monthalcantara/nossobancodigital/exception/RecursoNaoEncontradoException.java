package io.github.monthalcantara.nossobancodigital.exception;

import lombok.Getter;

import java.util.List;

public class RecursoNaoEncontradoException extends RuntimeException {
    @Getter
    List<String> erros;

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(List<String> listErrors) {
        this.erros = listErrors;
    }
}
