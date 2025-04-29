package br.com.wefit;

import br.com.wefit.domain.model.Endereco;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.model.TipoPessoa;

import java.util.UUID;

public class PessoaTexture {

    public static Pessoa criarPessoaValida(UUID id) {
        return new Pessoa(
                id,
                TipoPessoa.PESSOA_FISICA,
                null,
                "123.456.789-10",
                "João Silva",
                "11987654321",
                "1132345678",
                "joao@example.com",
                new Endereco(
                        "01001-000",
                        "Rua A",
                        "100",
                        "Apto 1",
                        "São Paulo",
                        "Centro",
                        "SP"
                ),
                true
        );
    }
}
