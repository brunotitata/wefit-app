package br.com.wefit.domain.model;

import br.com.wefit.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PessoaTest {

    private final Endereco enderecoValido = new Endereco(
            "01001-000",
            "Rua A",
            "100",
            "Apto 1",
            "São Paulo",
            "Centro",
            "SP"
    );

    @Test
    void deveCriarPessoaFisicaValida() {
        Pessoa pessoa = Pessoa.novaPessoa(
                TipoPessoa.PESSOA_FISICA,
                null,
                "123.456.789-10",
                "João Silva",
                "11987654321",
                "1132345678",
                "joao@example.com",
                enderecoValido,
                true
        );

        assertNotNull(pessoa);
        assertEquals(TipoPessoa.PESSOA_FISICA, pessoa.tipoPessoa());
        assertEquals("123.456.789-10", pessoa.cpfResponsavel());
        assertNull(pessoa.cnpj());
    }

    @Test
    void deveCriarPessoaJuridicaValida() {
        Pessoa pessoa = Pessoa.novaPessoa(
                TipoPessoa.PESSOA_JURIDICA,
                "12.345.678/0001-90",
                null,
                "Empresa XYZ",
                "11987654321",
                "1132345678",
                "empresa@example.com",
                enderecoValido,
                true
        );

        assertNotNull(pessoa);
        assertEquals(TipoPessoa.PESSOA_JURIDICA, pessoa.tipoPessoa());
        assertEquals("12.345.678/0001-90", pessoa.cnpj());
        assertNull(pessoa.cpfResponsavel());
    }

    @Test
    void deveLancarExcecaoParaCpfInvalido() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_FISICA,
                    null,
                    "12345678910",
                    "João Silva",
                    "11987654321",
                    "1132345678",
                    "joao@example.com",
                    enderecoValido,
                    true
            );
        });
        assertEquals("Formato de CPF inválido (esperado 000.000.000-00)", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaCnpjInvalido() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_JURIDICA,
                    "12345678000190",
                    null,
                    "Empresa XYZ",
                    "11987654321",
                    "1132345678",
                    "empresa@example.com",
                    enderecoValido,
                    true
            );
        });
        assertEquals("Formato de CNPJ inválido (esperado 00.000.000/0000-00)", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaPessoaFisicaComCnpjPreenchido() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_FISICA,
                    "12.345.678/0001-90",
                    "123.456.789-10",
                    "João Silva",
                    "11987654321",
                    "1132345678",
                    "joao@example.com",
                    enderecoValido,
                    true
            );
        });
        assertEquals("Pessoa Física não deve informar CNPJ", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoParaPessoaJuridicaComCpfPreenchido() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_JURIDICA,
                    "12.345.678/0001-90",
                    "123.456.789-10",
                    "Empresa XYZ",
                    "11987654321",
                    "1132345678",
                    "empresa@example.com",
                    enderecoValido,
                    true
            );
        });
        assertEquals("Pessoa Jurídica não deve informar CPF do responsável", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEmBranco() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_FISICA,
                    null,
                    "123.456.789-10",
                    "",
                    "11987654321",
                    "1132345678",
                    "joao@example.com",
                    enderecoValido,
                    true
            );
        });
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCelularEmBranco() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_FISICA,
                    null,
                    "123.456.789-10",
                    "João Silva",
                    "",
                    "1132345678",
                    "joao@example.com",
                    enderecoValido,
                    true
            );
        });
        assertEquals("Celular é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailEmBranco() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_FISICA,
                    null,
                    "123.456.789-10",
                    "João Silva",
                    "11987654321",
                    "1132345678",
                    "",
                    enderecoValido,
                    true
            );
        });
        assertEquals("Email é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoNulo() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_FISICA,
                    null,
                    "123.456.789-10",
                    "João Silva",
                    "11987654321",
                    "1132345678",
                    "joao@example.com",
                    null,
                    true
            );
        });
        assertEquals("Endereço é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNaoAceitarTermos() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            Pessoa.novaPessoa(
                    TipoPessoa.PESSOA_FISICA,
                    null,
                    "123.456.789-10",
                    "João Silva",
                    "11987654321",
                    "1132345678",
                    "joao@example.com",
                    enderecoValido,
                    false
            );
        });
        assertEquals("Você deve concordar com os termos de uso", exception.getMessage());
    }

    @Test
    void deveRetornarCpfQuandoPresente() {
        Pessoa pessoa = Pessoa.novaPessoa(
                TipoPessoa.PESSOA_FISICA,
                null,
                "123.456.789-10",
                "João Silva",
                "11987654321",
                "1132345678",
                "joao@example.com",
                enderecoValido,
                true
        );
        String cpfOuCnpj = Pessoa.getCpfOuCnpj(pessoa);
        assertEquals("123.456.789-10", cpfOuCnpj);
    }

    @Test
    void deveRetornarCnpjQuandoPresente() {
        Pessoa pessoa = Pessoa.novaPessoa(
                TipoPessoa.PESSOA_JURIDICA,
                "12.345.678/0001-90",
                null,
                "Empresa XYZ",
                "11987654321",
                "1132345678",
                "empresa@example.com",
                enderecoValido,
                true
        );
        String cpfOuCnpj = Pessoa.getCpfOuCnpj(pessoa);
        assertEquals("12.345.678/0001-90", cpfOuCnpj);
    }
}
