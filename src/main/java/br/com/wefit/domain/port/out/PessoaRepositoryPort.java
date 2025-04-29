package br.com.wefit.domain.port.out;

import br.com.wefit.domain.model.Pessoa;

import java.util.UUID;

public interface PessoaRepositoryPort {

    UUID salvar(Pessoa pessoa);

    Pessoa buscar(UUID pessoaId);

}
