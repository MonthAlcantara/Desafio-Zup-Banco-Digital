package io.github.monthalcantara.nossobancodigital.advice;

import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.exception.ViolacaoRegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrosApi recursoNaoEncontradoException(RecursoNaoEncontradoException e) {
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrosApi handleConstraintViolationException(ConstraintViolationException e) {
        return new ErrosApi(e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ErrosApi tamanhoMaximoArquivoException(MultipartException e) {
        return new ErrosApi(e.getMessage());

    }

    @ExceptionHandler(ViolacaoRegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrosApi violacaoRegraNegocioException(ViolacaoRegraNegocioException e) {
        return new ErrosApi(e.getMessage());
    }

}
