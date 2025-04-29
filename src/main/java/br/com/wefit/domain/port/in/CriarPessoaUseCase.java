package br.com.wefit.domain.port.in;

import br.com.wefit.application.dto.PessoaRequest;
import br.com.wefit.domain.model.Pessoa;

import java.util.UUID;

public interface CriarPessoaUseCase {

    UUID criar(PessoaRequest pessoaRequest);

    Pessoa buscarPorPessoaId(UUID pessoaId);

}
