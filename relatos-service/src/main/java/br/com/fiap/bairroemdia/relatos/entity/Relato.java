package br.com.fiap.bairroemdia.relatos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "relatos")
public class Relato {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", nullable = false, length = 80)
    private String titulo;
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    @Column(name = "categoria", nullable = false, length = 40)
    private String categoria;
    @Column(name = "logradouro", nullable = false, length = 120)
    private String logradouro;
    @Column(name = "bairro", nullable = false, length = 80)
    private String bairro;
    @Enumerated(EnumType.STRING)
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
    @Column(nullable = false, length = 24)
    private Status status;
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    protected Relato() {}

    public Relato(String titulo, String descricao, String categoria, String logradouro, String bairro, Status status) {
        atualizar(titulo, descricao, categoria, logradouro, bairro, status);
    }

    public void atualizar(String titulo, String descricao, String categoria, String logradouro, String bairro, Status status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.logradouro = logradouro;
        this.bairro = bairro;
        alterarStatus(status);
    }

    public void alterarStatus(Status status) {
        this.status = status;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public String getLogradouro() { return logradouro; }
    public String getBairro() { return bairro; }
    public Status getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
