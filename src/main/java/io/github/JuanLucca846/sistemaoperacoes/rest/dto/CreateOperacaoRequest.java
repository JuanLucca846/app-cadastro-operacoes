package io.github.JuanLucca846.sistemaoperacoes.rest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateOperacaoRequest {

    @NotBlank(message = "Nome obrigatório.")
    private String nome;

    @NotBlank(message = "Descrição obrigatória.")
    private String descricao;

    @NotBlank(message = "Categoria obrigatória.")
    private String categoria;

    @NotBlank(message = "Autenticação obrigatória.")
    private String autenticacao;

    @NotBlank(message = "Permissão obrigatória.")
    private String permissao;
}
