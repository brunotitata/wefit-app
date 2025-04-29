package br.com.wefit.infrastructure.web;

import br.com.wefit.domain.exception.DomainException;
import br.com.wefit.domain.exception.PessoaExistenteException;
import br.com.wefit.domain.exception.PessoaNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getField(),
                        e -> e.getDefaultMessage()
                ));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException formatException) {
            if (formatException.getTargetType().isEnum()
                    && formatException.getTargetType().equals(br.com.wefit.domain.model.TipoPessoa.class)) {

                String valoresValidos = Arrays.stream(br.com.wefit.domain.model.TipoPessoa.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Valor inválido para TipoPessoa. Valores permitidos: " + valoresValidos + "."));
            }
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Requisição mal formatada.");
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> domainException(DomainException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(PessoaExistenteException.class)
    public ResponseEntity<Map<String, String>> pessoaExistenteException(PessoaExistenteException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<Map<String, String>> pessoaNotFoundException(PessoaNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

}