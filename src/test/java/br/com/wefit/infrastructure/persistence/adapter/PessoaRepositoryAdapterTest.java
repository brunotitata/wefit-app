package br.com.wefit.infrastructure.persistence.adapter;

import br.com.wefit.domain.exception.PessoaNotFoundException;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.infrastructure.persistence.jpa.PessoaEntity;
import br.com.wefit.infrastructure.persistence.jpa.PessoaJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static br.com.wefit.PessoaEntityTexture.criarPessoaEntityValida;
import static br.com.wefit.PessoaTexture.criarPessoaValida;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PessoaRepositoryAdapterTest {

    private PessoaJpaRepository repository;
    private PessoaRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PessoaJpaRepository.class);
        adapter = new PessoaRepositoryAdapter(repository);
    }

    @Test
    @DisplayName("Deve salvar uma pessoa corretamente")
    void deveSalvarPessoaCorretamente() {

        Pessoa pessoa = criarPessoaValida(UUID.randomUUID());

        UUID result = adapter.salvar(pessoa);

        assertEquals(pessoa.pessoaId(), result);

        ArgumentCaptor<PessoaEntity> captor = ArgumentCaptor.forClass(PessoaEntity.class);

        verify(repository).salvar(eq(pessoa.cpfResponsavel()), captor.capture());

        PessoaEntity entitySalva = captor.getValue();
        assertEquals(pessoa.nome(), entitySalva.getNome());
        assertEquals(pessoa.email(), entitySalva.getEmail());
        assertEquals(pessoa.celular(), entitySalva.getCelular());
        assertEquals(pessoa.tipoPessoa().name(), entitySalva.getTipoPessoa().name());
    }

    @Test
    @DisplayName("Deve buscar uma pessoa existente")
    void deveBuscarPessoaExistente() {

        UUID pessoaId = UUID.randomUUID();
        PessoaEntity entity = criarPessoaEntityValida(pessoaId);

        when(repository.findByPessoaId(pessoaId)).thenReturn(Optional.of(entity));

        Pessoa pessoa = adapter.buscar(pessoaId);

        assertNotNull(pessoa);
        assertEquals(entity.getPessoaId(), pessoa.pessoaId());
        assertEquals(entity.getNome(), pessoa.nome());
        assertEquals(entity.getEmail(), pessoa.email());
        assertEquals(entity.getCelular(), pessoa.celular());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar pessoa inexistente")
    void deveLancarExcecaoQuandoPessoaNaoEncontrada() {

        UUID pessoaId = UUID.randomUUID();
        when(repository.findByPessoaId(pessoaId)).thenReturn(Optional.empty());

        PessoaNotFoundException exception = assertThrows(PessoaNotFoundException.class, () -> {
            adapter.buscar(pessoaId);
        });

        assertEquals("Pessoa não encontrada", exception.getMessage());
    }

}
