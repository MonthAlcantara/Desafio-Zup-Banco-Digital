package io.github.monthalcantara.nossobancodigital.advice;

import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.exception.ViolacaoRegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrosApi recursoNaoEncontrado(RecursoNaoEncontradoException e) {
        return new ErrosApi(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrosApi handleMethodNotValidException(MethodArgumentNotValidException e) {
        List<String> listErrors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ErrosApi(listErrors);
    }

    @ExceptionHandler(ViolacaoRegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrosApi violacaoRegraNegocio(ViolacaoRegraNegocioException e) {
            return new ErrosApi(e.getMessage());
        }

}
