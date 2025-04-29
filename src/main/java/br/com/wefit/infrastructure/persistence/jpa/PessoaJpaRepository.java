package br.com.wefit.infrastructure.persistence.jpa;

import br.com.wefit.domain.exception.PessoaExistenteException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PessoaJpaRepository extends JpaRepository<PessoaEntity, Long> {

    Optional<PessoaEntity> findByCnpjOrCpfResponsavel(String cnpj, String cpfResponsavel);

    Optional<PessoaEntity> findByPessoaId(UUID pessoaId);

    default PessoaEntity salvar(String documento, PessoaEntity novaEntidade) {

        findByCnpjOrCpfResponsavel(documento, documento)
                .ifPresent(existing -> {
                    throw new PessoaExistenteException(
                            "Já existe um perfil cadastrado com CPF/CNPJ = " + documento
                    );
                });

        return save(novaEntidade);
    }

}