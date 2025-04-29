package br.com.wefit;

import br.com.wefit.domain.port.in.CriarPessoaUseCase;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ConfigTest {

    @Bean
    public CriarPessoaUseCase criarPessoaUseCase() {
        return Mockito.mock(CriarPessoaUseCase.class);
    }

}