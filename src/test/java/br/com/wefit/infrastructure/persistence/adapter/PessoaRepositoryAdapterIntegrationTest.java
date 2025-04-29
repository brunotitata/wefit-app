package br.com.wefit.infrastructure.persistence.adapter;

import br.com.wefit.domain.model.Endereco;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.model.TipoPessoa;
import br.com.wefit.infrastructure.persistence.jpa.PessoaJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
class PessoaRepositoryAdapterIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.1")
            .withDatabaseName("wefit")
            .withUsername("postgres")
            .withPassword("admin");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private PessoaJpaRepository pessoaJpaRepository;

    private PessoaRepositoryAdapter pessoaRepositoryAdapter;

    @BeforeEach
    void setup() {
        pessoaRepositoryAdapter = new PessoaRepositoryAdapter(pessoaJpaRepository);
    }

    @Test
    @DisplayName("Deve salvar e buscar uma Pessoa no banco de dados real")
    void deveSalvarEBuscarPessoa() {

        Endereco endereco = new Endereco("01001-000", "Rua Teste", "123", "Apto 1", "São Paulo", "Centro", "SP");
        Pessoa pessoa = Pessoa.novaPessoa(
                TipoPessoa.PESSOA_FISICA,
                null,
                "123.456.789-10",
                "João Teste",
                "11999999999",
                "1144445555",
                "joao.teste@example.com",
                endereco,
                true
        );

        UUID pessoaId = pessoaRepositoryAdapter.salvar(pessoa);
        Pessoa pessoaEncontrada = pessoaRepositoryAdapter.buscar(pessoaId);

        assertNotNull(pessoaEncontrada);
        assertEquals(pessoa.nome(), pessoaEncontrada.nome());
        assertEquals(pessoa.email(), pessoaEncontrada.email());

    }

}
