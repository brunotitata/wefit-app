package br.com.wefit.application.dto;

import br.com.wefit.domain.exception.DomainException;
import br.com.wefit.domain.model.TipoPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static br.com.wefit.domain.model.TipoPessoa.PESSOA_FISICA;
import static br.com.wefit.domain.model.TipoPessoa.PESSOA_JURIDICA;

@Builder(toBuilder = true)
public record PessoaRequest(

        @NotNull(message = "Tipo de pessoa é obrigatório")
        TipoPessoa tipoPessoa,

        @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}",
                message = "CNPJ deve estar no formato 00.000.000/0000-00")
        String cnpj,

        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                message = "CPF do responsável deve estar no formato 000.000.000-00")
        String cpfResponsavel,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Celular é obrigatório")
        String celular,

        String telefone,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Confirmação de email é obrigatória")
        String confirmEmail,

        @Valid
        @NotNull(message = "Endereço é obrigatório")
        EnderecoRequest endereco,

        @NotNull(message = "Você deve concordar com os termos de uso")
        Boolean aceitarTermos
) {

    public PessoaRequest {
        if (email != null && confirmEmail != null && !email.equals(confirmEmail)) {
            throw new DomainException("Email e confirmação de email devem ser iguais");
        }

        if (tipoPessoa != null) {
            if (tipoPessoa == PESSOA_FISICA) {
                if (cnpj != null && !cnpj.isBlank()) {
                    throw new DomainException("Para Pessoa Física, o CNPJ deve ser vazio");
                }
            } else if (tipoPessoa == PESSOA_JURIDICA) {
                if (cnpj == null || cnpj.isBlank() || cpfResponsavel == null || cpfResponsavel.isBlank()) {
                    throw new DomainException("Para Pessoa Jurídica, informe tanto o CNPJ quanto o CPF do responsável");
                }
            } else {
                throw new DomainException("Tipo de pessoa inválido");
            }
        }
    }
}
