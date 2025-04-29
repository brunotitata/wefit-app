package br.com.wefit.infrastructure.event;

import br.com.wefit.domain.model.Pessoa;
import br.com.wefit.domain.port.out.EventPublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class PessoaEventPublisherAdapter implements EventPublisherPort {

    private final ApplicationEventPublisher springPublisher;

    public PessoaEventPublisherAdapter(ApplicationEventPublisher springPublisher) {
        this.springPublisher = springPublisher;
    }

    @Override
    public void publicarEvento(Pessoa pessoa) {

        log.info("Publicando evento de dominio... " + pessoa.pessoaId());

        springPublisher.publishEvent(
                new ProfileCreatedEvent(pessoa.pessoaId(), LocalDateTime.now())
        );
    }

    public record ProfileCreatedEvent(UUID pessoaId, LocalDateTime dataOcorrido) { }

}