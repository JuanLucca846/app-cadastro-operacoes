package io.github.JuanLucca846.sistemaoperacoes.domain.repository;

import io.github.JuanLucca846.sistemaoperacoes.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
}
