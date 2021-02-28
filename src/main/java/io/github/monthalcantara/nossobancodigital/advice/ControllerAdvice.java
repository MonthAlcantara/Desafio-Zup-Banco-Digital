package io.github.monthalcantara.nossobancodigital.advice;

import io.github.monthalcantara.nossobancodigital.exception.RecursoNaoEncontradoException;
import io.github.monthalcantara.nossobancodigital.exception.ViolacaoRegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity recursoNaoEncontradoException(RecursoNaoEncontradoException e) {
        return new ResponseEntity(transformError(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodNotValidException(MethodArgumentNotValidException e) {
        List<String> listErrors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity(new ErrosApi(listErrors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity(transformError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity tamanhoMaximoArquivoException(MultipartException e) {
        return new ResponseEntity(transformError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ViolacaoRegraNegocioException.class)
    public ResponseEntity violacaoRegraNegocioException(ViolacaoRegraNegocioException e) {
        return new ResponseEntity(transformError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity(transformError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity illegalStateException(IllegalStateException e) {
        return new ResponseEntity(transformError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private ErrosApi transformError(String message) {
        return new ErrosApi(message);
    }
}
