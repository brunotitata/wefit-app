package br.com.wefit.infrastructure.web;

import br.com.wefit.application.dto.PessoaRequest;
import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.port.in.CriarPessoaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pessoa")
@Tag(name = "API para gerenciamento de pessoas")
public class PessoaController {

    private final CriarPessoaUseCase criarPessoaUseCase;

    public PessoaController(CriarPessoaUseCase criarPessoaUseCase) {
        this.criarPessoaUseCase = criarPessoaUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar nova pessoa do tipo PESSOA_FISICA ou PESSOA_JURIDICA", description = "Cria uma nova pessoa e retorna a URI do recurso criado.")
    public ResponseEntity<Void> criarPessoa(@Valid @RequestBody PessoaRequest pessoaRequest) {

        UUID pessoaId = criarPessoaUseCase.criar(pessoaRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pessoaId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{pessoaId}")
    @Operation(summary = "Buscar pessoa por ID", description = "Busca uma pessoa cadastrada pelo seu identificador UUID.")
    public ResponseEntity<Pessoa> buscarPessoa(
            @Parameter(description = "Identificador da pessoa") @PathVariable UUID pessoaId) {
        return ResponseEntity.ok(criarPessoaUseCase.buscarPorPessoaId(pessoaId));
    }

}
