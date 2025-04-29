package br.com.wefit.domain.port.out;

import br.com.wefit.domain.model.Pessoa;

public interface EventPublisherPort {

    void publicarEvento(Pessoa pessoa);

}
