package io.github.JuanLucca846.sistemaoperacoes.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_operacoes")
@Data
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "requisicao")
    private String requisicao;

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
