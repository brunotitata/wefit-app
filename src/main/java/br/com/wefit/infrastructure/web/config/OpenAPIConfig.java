package br.com.wefit.infrastructure.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wefit API")
                        .description("API de Gerenciamento de Perfis de Usuário - Projeto Wefit")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Wefit")
                                .url("https://wefit.com.br")
                                .email("contato@wefit.com.br")));
    }

}
