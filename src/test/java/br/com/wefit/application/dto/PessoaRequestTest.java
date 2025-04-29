package br.com.wefit.application.dto;

import br.com.wefit.domain.exception.DomainException;
import br.com.wefit.domain.model.TipoPessoa;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PessoaRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar PessoaRequest válido")
    void deveCriarPessoaRequestValido() {
        PessoaRequest request = criarPessoaRequestValida();

        Set<ConstraintViolation<PessoaRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Não deve haver violações para objeto válido");
    }

    @Test
    @DisplayName("Deve lançar exceção se emails forem diferentes")
    void deveLancarExcecaoSeEmailsForemDiferentes() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            criarPessoaRequestValida().toBuilder()
                    .confirmEmail("outro@email.com")
                    .build();
        });

        assertEquals("Email e confirmação de email devem ser iguais", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se PF preencher CNPJ")
    void deveLancarExcecaoParaPessoaFisicaComCnpjPreenchido() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            criarPessoaRequestValida().toBuilder()
                    .cnpj("12.345.678/0001-90")
                    .build();
        });

        assertEquals("Para Pessoa Física, o CNPJ deve ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção se PJ não preencher CNPJ e CPF")
    void deveLancarExcecaoParaPessoaJuridicaSemCnpjOuCpf() {
        DomainException exception = assertThrows(DomainException.class, () -> {
            criarPessoaRequestValida().toBuilder()
                    .tipoPessoa(TipoPessoa.PESSOA_JURIDICA)
                    .cnpj(null)
                    .cpfResponsavel(null)
                    .build();
        });

        assertEquals("Para Pessoa Jurídica, informe tanto o CNPJ quanto o CPF do responsável", exception.getMessage());
    }

    @Test
    @DisplayName("Deve detectar campos obrigatórios faltando")
    void deveDetectarCamposObrigatoriosFaltando() {
        PessoaRequest request = PessoaRequest.builder().build();

        Set<ConstraintViolation<PessoaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Tipo de pessoa é obrigatório")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Nome é obrigatório")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Celular é obrigatório")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Email é obrigatório")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Confirmação de email é obrigatória")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Endereço é obrigatório")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Você deve concordar com os termos de uso")));
    }

    @Test
    @DisplayName("Deve validar formato inválido de CPF")
    void deveValidarFormatoInvalidoDeCpf() {
        PessoaRequest request = criarPessoaRequestValida().toBuilder()
                .cpfResponsavel("12345678910") // errado
                .build();

        Set<ConstraintViolation<PessoaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().contains("CPF do responsável deve estar no formato")
        ));
    }

    @Test
    @DisplayName("Deve validar formato inválido de CNPJ")
    void deveValidarFormatoInvalidoDeCnpj() {
        PessoaRequest request = criarPessoaRequestValida().toBuilder()
                .tipoPessoa(TipoPessoa.PESSOA_JURIDICA)
                .cnpj("12345678000190") // errado
                .cpfResponsavel("123.456.789-10")
                .build();

        Set<ConstraintViolation<PessoaRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(
                v -> v.getMessage().contains("CNPJ deve estar no formato")
        ));
    }

    private PessoaRequest criarPessoaRequestValida() {
        return PessoaRequest.builder()
                .tipoPessoa(TipoPessoa.PESSOA_FISICA)
                .cpfResponsavel("123.456.789-10")
                .nome("João Silva")
                .celular("11987654321")
                .telefone("1132345678")
                .email("joao@example.com")
                .confirmEmail("joao@example.com")
                .endereco(EnderecoRequest.builder()
                        .cep("01001-000")
                        .logradouro("Rua A")
                        .numero("100")
                        .complemento("Apto 1")
                        .cidade("São Paulo")
                        .bairro("Centro")
                        .estado("SP")
                        .build())
                .aceitarTermos(true)
                .build();
    }
}
