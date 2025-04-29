package br.com.wefit.domain.model;

import br.com.wefit.domain.exception.DomainException;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public record Pessoa(
        UUID pessoaId,
        TipoPessoa tipoPessoa,
        String cnpj,
        String cpfResponsavel,
        String nome,
        String celular,
        String telefone,
        String email,
        Endereco endereco,
        boolean aceitarTermos
) {

    private static final Pattern CPF_REGEX = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Pattern CNPJ_REGEX = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");

    public Pessoa {
        if (tipoPessoa == null) {
            throw new DomainException("Tipo de pessoa é obrigatório");
        }
        if (nome == null || nome.isBlank()) {
            throw new DomainException("Nome é obrigatório");
        }
        if (celular == null || celular.isBlank()) {
            throw new DomainException("Celular é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new DomainException("Email é obrigatório");
        }
        if (!aceitarTermos) {
            throw new DomainException("Você deve concordar com os termos de uso");
        }

        Objects.requireNonNull(endereco, "Endereço é obrigatório");

        if (tipoPessoa == TipoPessoa.PESSOA_FISICA) {

            if (cpfResponsavel == null || cpfResponsavel.isBlank()) {
                throw new DomainException("CPF é obrigatório para Pessoa Física");
            }
            if (!CPF_REGEX.matcher(cpfResponsavel).matches()) {
                throw new DomainException("Formato de CPF inválido (esperado 000.000.000-00)");
            }
            if (cnpj != null && !cnpj.isBlank()) {
                throw new DomainException("Pessoa Física não deve informar CNPJ");
            }

        } else if (tipoPessoa == TipoPessoa.PESSOA_JURIDICA) {

            if (cnpj == null || cnpj.isBlank()) {
                throw new DomainException("CNPJ é obrigatório para Pessoa Jurídica");
            }
            if (!CNPJ_REGEX.matcher(cnpj).matches()) {
                throw new DomainException("Formato de CNPJ inválido (esperado 00.000.000/0000-00)");
            }
            if (cpfResponsavel != null && !cpfResponsavel.isBlank()) {
                throw new DomainException("Pessoa Jurídica não deve informar CPF do responsável");
            }

        } else {
            throw new DomainException("Tipo de pessoa inválido");
        }
    }

    public static Pessoa novaPessoa(
            TipoPessoa tipoPessoa,
            String cnpj,
            String cpfResponsavel,
            String nome,
            String celular,
            String telefone,
            String email,
            Endereco endereco,
            boolean aceitarTermos
    ) {
        return new Pessoa(
                UUID.randomUUID(),
                tipoPessoa,
                cnpj,
                cpfResponsavel,
                nome,
                celular,
                telefone,
                email,
                endereco,
                aceitarTermos
        );
    }

    public static String getCpfOuCnpj(Pessoa pessoa) {
        return (pessoa.cnpj() != null && !pessoa.cnpj().isBlank())
                ? pessoa.cnpj()
                : pessoa.cpfResponsavel();
    }

}