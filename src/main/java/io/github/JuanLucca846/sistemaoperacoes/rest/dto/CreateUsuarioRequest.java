package io.github.JuanLucca846.sistemaoperacoes.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateUsuarioRequest {

    @NotBlank(message = "Nome obrigatório.")
    private String nome;

    @NotNull(message = "Idade obrigatória.")
    private Integer idade;

    @NotBlank(message = "Email obrigatória.")
    private String email;

    @NotBlank(message = "Senha obrigatória.")
    private String senha;
}
