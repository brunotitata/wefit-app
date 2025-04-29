package br.com.wefit.application.service;

import br.com.wefit.application.dto.EnderecoRequest;
import br.com.wefit.application.dto.PessoaRequest;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.model.TipoPessoa;
import br.com.wefit.domain.port.out.EventPublisherPort;
import br.com.wefit.domain.port.out.PessoaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PessoaApplicationServiceTest {

    private PessoaRepositoryPort repository;
    private EventPublisherPort publisher;
    private PessoaApplicationService service;

    @BeforeEach
    void setUp() {
        repository = mock(PessoaRepositoryPort.class);
        publisher = mock(EventPublisherPort.class);
        service = new PessoaApplicationService(repository, publisher);
    }

    @Test
    void deveCriarPessoaComSucesso() {

        PessoaRequest request = PessoaRequest.builder()
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


        UUID pessoaId = service.criar(request);

        assertNotNull(pessoaId);

        ArgumentCaptor<Pessoa> pessoaCaptor = ArgumentCaptor.forClass(Pessoa.class);

        verify(repository).salvar(pessoaCaptor.capture());
        verify(publisher).publicarEvento(pessoaCaptor.getValue());

        Pessoa pessoaSalva = pessoaCaptor.getValue();

        assertEquals(request.nome(), pessoaSalva.nome());
        assertEquals(request.email(), pessoaSalva.email());
        assertEquals(request.celular(), pessoaSalva.celular());
        assertEquals(request.cpfResponsavel(), pessoaSalva.cpfResponsavel());
        assertEquals(request.tipoPessoa(), pessoaSalva.tipoPessoa());
        assertNotNull(pessoaSalva.pessoaId());

    }

}
