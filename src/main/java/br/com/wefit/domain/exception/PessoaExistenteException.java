package br.com.wefit.domain.exception;

public class PessoaExistenteException extends RuntimeException {
    public PessoaExistenteException(String message) {
        super(message);
    }
}
