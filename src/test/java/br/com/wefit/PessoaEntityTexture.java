package br.com.wefit;

import br.com.wefit.infrastructure.persistence.jpa.EnderecoEntity;
import br.com.wefit.infrastructure.persistence.jpa.PessoaEntity;
import br.com.wefit.infrastructure.persistence.jpa.TipoPessoaEntity;

import java.util.UUID;

public class PessoaEntityTexture {

    public static PessoaEntity criarPessoaEntityValida(UUID id) {
        return PessoaEntity.builder()
                .id(1L)
                .pessoaId(id)
                .tipoPessoa(TipoPessoaEntity.PESSOA_FISICA)
                .cpfResponsavel("123.456.789-10")
                .nome("João Silva")
                .celular("11987654321")
                .telefone("1132345678")
                .email("joao@example.com")
                .endereco(EnderecoEntity.builder()
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
