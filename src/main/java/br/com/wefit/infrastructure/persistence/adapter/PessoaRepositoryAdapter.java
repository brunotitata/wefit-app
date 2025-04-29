package br.com.wefit.infrastructure.persistence.adapter;

import br.com.wefit.domain.exception.PessoaNotFoundException;
import br.com.wefit.domain.model.Endereco;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.model.TipoPessoa;
import br.com.wefit.domain.port.out.PessoaRepositoryPort;
import br.com.wefit.infrastructure.persistence.jpa.EnderecoEntity;
import br.com.wefit.infrastructure.persistence.jpa.PessoaEntity;
import br.com.wefit.infrastructure.persistence.jpa.PessoaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.com.wefit.domain.model.Pessoa.getCpfOuCnpj;
import static br.com.wefit.infrastructure.persistence.jpa.TipoPessoaEntity.valueOf;

@Component
public class PessoaRepositoryAdapter implements PessoaRepositoryPort {

    private final PessoaJpaRepository repository;

    public PessoaRepositoryAdapter(PessoaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID salvar(Pessoa pessoa) {

        var entity = PessoaEntity.builder()
                .id(null) // ID gerado automaticamente
                .pessoaId(pessoa.pessoaId())
                .tipoPessoa(valueOf(pessoa.tipoPessoa().name()))
                .cnpj(pessoa.cnpj())
                .cpfResponsavel(pessoa.cpfResponsavel())
                .nome(pessoa.nome())
                .celular(pessoa.celular())
                .telefone(pessoa.telefone())
                .email(pessoa.email())
                .endereco(EnderecoEntity.builder()
                        .cep(pessoa.endereco().cep())
                        .logradouro(pessoa.endereco().logradouro())
                        .numero(pessoa.endereco().numero())
                        .complemento(pessoa.endereco().complemento())
                        .cidade(pessoa.endereco().cidade())
                        .bairro(pessoa.endereco().bairro())
                        .estado(pessoa.endereco().estado())
                        .build())
                .aceitarTermos(pessoa.aceitarTermos())
                .build();

        String documento = getCpfOuCnpj(pessoa);
        repository.salvar(documento, entity);

        return pessoa.pessoaId();
    }

    @Override
    public Pessoa buscar(UUID pessoaId) {
        return repository.findByPessoaId(pessoaId).map(pessoa -> {

            Endereco endereco = new Endereco(
                    pessoa.getEndereco().getCep(),
                    pessoa.getEndereco().getLogradouro(),
                    pessoa.getEndereco().getNumero(),
                    pessoa.getEndereco().getComplemento(),
                    pessoa.getEndereco().getCidade(),
                    pessoa.getEndereco().getBairro(),
                    pessoa.getEndereco().getEstado()
            );

            return new Pessoa(
                    pessoa.getPessoaId(),
                    TipoPessoa.valueOf(pessoa.getTipoPessoa().name()),
                    pessoa.getCnpj(),
                    pessoa.getCpfResponsavel(),
                    pessoa.getNome(),
                    pessoa.getCelular(),
                    pessoa.getTelefone(),
                    pessoa.getEmail(),
                    endereco,
                    pessoa.isAceitarTermos()
            );

        }).orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada"));
    }

}