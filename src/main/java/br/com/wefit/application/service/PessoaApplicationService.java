package br.com.wefit.application.service;

import br.com.wefit.application.dto.PessoaRequest;
import br.com.wefit.domain.model.Endereco;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.port.in.CriarPessoaUseCase;
import br.com.wefit.domain.port.out.EventPublisherPort;
import br.com.wefit.domain.port.out.PessoaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PessoaApplicationService implements CriarPessoaUseCase {

    private final PessoaRepositoryPort repository;
    private final EventPublisherPort publisher;

    public PessoaApplicationService(PessoaRepositoryPort repository, EventPublisherPort publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public UUID criar(PessoaRequest pessoaRequest) {

        var enderecoRequest = pessoaRequest.endereco();

        var endereco = new Endereco(
                enderecoRequest.cep(),
                enderecoRequest.logradouro(),
                enderecoRequest.numero(),
                enderecoRequest.complemento(),
                enderecoRequest.cidade(),
                enderecoRequest.bairro(),
                enderecoRequest.estado()
        );

        var pessoa = Pessoa.novaPessoa(
                pessoaRequest.tipoPessoa(),
                pessoaRequest.cnpj(),
                pessoaRequest.cpfResponsavel(),
                pessoaRequest.nome(),
                pessoaRequest.celular(),
                pessoaRequest.telefone(),
                pessoaRequest.email(),
                endereco,
                pessoaRequest.aceitarTermos()
        );

        repository.salvar(pessoa);

        // Podemos usar RabbitMQ, Kafka, SQS, etc
        publisher.publicarEvento(pessoa);

        return pessoa.pessoaId();
    }

    @Override
    public Pessoa buscarPorPessoaId(UUID pessoaId) {
        return repository.buscar(pessoaId);
    }
}
