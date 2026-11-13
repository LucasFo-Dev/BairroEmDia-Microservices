package br.com.fiap.bairroemdia.atendimentos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
public class Atendimento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "relato_id", nullable = false)
    private Long relatoId;
    @Column(name = "equipe", nullable = false, length = 100)
    private String equipe;
    @Column(name = "responsavel", nullable = false, length = 100)
    private String responsavel;
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    @Column(name = "prioridade", nullable = false, length = 30)
    private String prioridade;
    @Enumerated(EnumType.STRING)
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
    @Column(nullable = false, length = 24)
    private Status status;
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    protected Atendimento() {}

    public Atendimento(Long relatoId, String equipe, String responsavel, String descricao, String prioridade, Status status) {
        atualizar(relatoId, equipe, responsavel, descricao, prioridade, status);
    }

    public void atualizar(Long relatoId, String equipe, String responsavel, String descricao, String prioridade, Status status) {
        this.relatoId = relatoId;
        this.equipe = equipe;
        this.responsavel = responsavel;
        this.descricao = descricao;
        this.prioridade = prioridade;
        alterarStatus(status);
    }

    public void alterarStatus(Status status) {
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getRelatoId() { return relatoId; }
    public String getEquipe() { return equipe; }
    public String getResponsavel() { return responsavel; }
    public String getDescricao() { return descricao; }
    public String getPrioridade() { return prioridade; }
    public Status getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
