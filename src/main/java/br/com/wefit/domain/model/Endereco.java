package br.com.wefit.domain.model;

import br.com.wefit.domain.exception.DomainException;

public record Endereco(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String cidade,
        String bairro,
        String estado
) {
    public Endereco {
        if (cep == null || cep.isBlank()) {
            throw new DomainException("CEP é obrigatório");
        }
        if (logradouro == null || logradouro.isBlank()) {
            throw new DomainException("Logradouro é obrigatório");
        }
        if (numero == null || numero.isBlank()) {
            throw new DomainException("Número é obrigatório");
        }
        if (cidade == null || cidade.isBlank()) {
            throw new DomainException("Cidade é obrigatória");
        }
        if (estado == null || estado.isBlank()) {
            throw new DomainException("Estado é obrigatório");
        }
    }
}