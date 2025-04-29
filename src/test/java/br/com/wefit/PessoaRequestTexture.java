package br.com.wefit;

import br.com.wefit.application.dto.EnderecoRequest;
import br.com.wefit.application.dto.PessoaRequest;
import br.com.wefit.domain.model.TipoPessoa;

public class PessoaRequestTexture {

    public static PessoaRequest criarPessoaRequestValida() {
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
