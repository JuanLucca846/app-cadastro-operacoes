package io.github.JuanLucca846.sistemaoperacoes.domain.repository;

import io.github.JuanLucca846.sistemaoperacoes.domain.model.Operacao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OperacoesRepository implements PanacheRepository<Operacao> {

    public List<Operacao> findByNome(String nome) {
        PanacheQuery<Operacao> query = find("nome", nome);
        return query.list();
    }

    public List<Operacao> findByDescricao(String descricao) {
        PanacheQuery<Operacao> query = find("descricao", descricao);
        return query.list();
    }

    public List<Operacao> findByCategoria(String categoria) {
        PanacheQuery<Operacao> query = find("categoria", categoria);
        return query.list();
    }
}
