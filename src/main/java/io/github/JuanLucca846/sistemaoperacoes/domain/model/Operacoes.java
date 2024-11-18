package io.github.JuanLucca846.sistemaoperacoes.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Operacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "requsicao")
    private String requsicao;

    @Column(name = "resposta")
    private String resposta;

    @Column(name = "autenticacao")
    private String autenticacao;

    @Column(name = "permissao")
    private String permissao;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @PrePersist
    public void prePersist() {
        dateTime = LocalDateTime.now();
    }
}
