package br.com.wefit.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record EnderecoRequest(

        @NotBlank(message = "CEP é obrigatório")
        String cep,

        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,

        @NotBlank(message = "Número é obrigatório")
        String numero,

        String complemento,

        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        String bairro,

        @NotBlank(message = "Estado é obrigatório")
        String estado
) {
}
