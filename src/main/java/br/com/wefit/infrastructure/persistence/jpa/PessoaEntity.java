package br.com.wefit.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pessoa_id", nullable = false, unique = true)
    private UUID pessoaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false)
    private TipoPessoaEntity tipoPessoa;

    @Column(length = 18)
    private String cnpj;

    @Column(name = "cpf_responsavel", length = 14)
    private String cpfResponsavel;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 18)
    private String celular;

    @Column(length = 18)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Embedded
    private EnderecoEntity endereco;

    @Column(name = "aceitar_termos", nullable = false)
    private boolean aceitarTermos;

}
