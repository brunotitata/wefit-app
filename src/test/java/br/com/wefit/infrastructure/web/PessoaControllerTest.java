package br.com.wefit.infrastructure.web;

import br.com.wefit.ConfigTest;
import br.com.wefit.application.dto.PessoaRequest;
import br.com.wefit.domain.exception.PessoaNotFoundException;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.port.in.CriarPessoaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static br.com.wefit.PessoaRequestTexture.criarPessoaRequestValida;
import static br.com.wefit.PessoaTexture.criarPessoaValida;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PessoaController.class)
@Import(ConfigTest.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CriarPessoaUseCase criarPessoaUseCase;

    @Test
    @DisplayName("Deve criar pessoa e retornar 201 Created com Location correto")
    void deveCriarPessoaComSucesso() throws Exception {

        UUID pessoaId = UUID.randomUUID();
        Mockito.when(criarPessoaUseCase.criar(any(PessoaRequest.class))).thenReturn(pessoaId);

        PessoaRequest request = criarPessoaRequestValida();

        mockMvc.perform(post("/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/pessoa/" + pessoaId));
    }

    @Test
    @DisplayName("Deve buscar pessoa existente e retornar 200 OK com dados")
    void deveBuscarPessoaExistente() throws Exception {

        UUID pessoaId = UUID.randomUUID();
        Pessoa pessoa = criarPessoaValida(pessoaId);

        Mockito.when(criarPessoaUseCase.buscarPorPessoaId(eq(pessoaId))).thenReturn(pessoa);

        mockMvc.perform(get("/pessoa/{pessoaId}", pessoaId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pessoaId").value(pessoaId.toString()))
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found ao buscar pessoa inexistente")
    void deveRetornarNotFoundQuandoPessoaNaoEncontrada() throws Exception {

        UUID pessoaId = UUID.randomUUID();
        Mockito.when(criarPessoaUseCase.buscarPorPessoaId(eq(pessoaId)))
                .thenThrow(new PessoaNotFoundException("Pessoa não encontrada"));

        mockMvc.perform(get("/pessoa/{pessoaId}", pessoaId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
