package io.github.JuanLucca846.sistemaoperacoes.domain.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_requisicao")
@Data
public class Requisicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "operacao_id")
    @JsonbTransient
    private Operacao operacao;
}
