package br.com.wefit.domain.model;

import br.com.wefit.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnderecoTest {

    @Test
    void deveCriarEnderecoValido() {
        Endereco endereco = new Endereco(
                "01001-000",
                "Rua A",
                "100",
                "Apto 1",
                "São Paulo",
                "Centro",
                "SP"
        );

        assertNotNull(endereco);
        assertEquals("01001-000", endereco.cep());
        assertEquals("Rua A", endereco.logradouro());
        assertEquals("100", endereco.numero());
        assertEquals("Apto 1", endereco.complemento());
        assertEquals("São Paulo", endereco.cidade());
        assertEquals("Centro", endereco.bairro());
        assertEquals("SP", endereco.estado());
    }

    @Test
    void deveLancarExcecaoQuandoCepVazio() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Endereco(
                    "",
                    "Rua A",
                    "100",
                    "Apto 1",
                    "São Paulo",
                    "Centro",
                    "SP"
            );
        });
        assertEquals("CEP é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoLogradouroVazio() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Endereco(
                    "01001-000",
                    "",
                    "100",
                    "Apto 1",
                    "São Paulo",
                    "Centro",
                    "SP"
            );
        });
        assertEquals("Logradouro é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNumeroVazio() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Endereco(
                    "01001-000",
                    "Rua A",
                    "",
                    "Apto 1",
                    "São Paulo",
                    "Centro",
                    "SP"
            );
        });
        assertEquals("Número é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCidadeVazia() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Endereco(
                    "01001-000",
                    "Rua A",
                    "100",
                    "Apto 1",
                    "",
                    "Centro",
                    "SP"
            );
        });
        assertEquals("Cidade é obrigatória", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEstadoVazio() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Endereco(
                    "01001-000",
                    "Rua A",
                    "100",
                    "Apto 1",
                    "São Paulo",
                    "Centro",
                    ""
            );
        });
        assertEquals("Estado é obrigatório", exception.getMessage());
    }
}
