package io.github.monthalcantara.nossobancodigital.exception;

import java.util.List;

public class RecursoNaoEncontradoException extends RuntimeException {

    private List<String> erros;

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(List<String> listErrors) {
        this.erros = listErrors;
    }

    public List<String> getErros() {
        return erros;
    }
}
