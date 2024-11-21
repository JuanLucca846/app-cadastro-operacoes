package io.github.JuanLucca846.sistemaoperacoes.rest.resource;

import io.github.JuanLucca846.sistemaoperacoes.domain.model.Operacao;
import io.github.JuanLucca846.sistemaoperacoes.domain.model.Usuario;
import io.github.JuanLucca846.sistemaoperacoes.domain.repository.OperacoesRepository;
import io.github.JuanLucca846.sistemaoperacoes.domain.repository.UsuarioRepository;
import io.github.JuanLucca846.sistemaoperacoes.rest.dto.CreateOperacaoRequest;
import io.github.JuanLucca846.sistemaoperacoes.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

@Path("/api/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OperacoesResource {

    private final UsuarioRepository usuarioRepository;
    private final OperacoesRepository operacoesRepository;
    private final Validator validator;

    @Inject
    public OperacoesResource(UsuarioRepository usuarioRepository, OperacoesRepository operacoesRepository, Validator validator) {
        this.usuarioRepository = usuarioRepository;
        this.operacoesRepository = operacoesRepository;
        this.validator = validator;
    }

    @POST
    @Path("usuarios/{usuarioId}/operacoes")
    @Transactional
    public Response criarOperacao(@PathParam("usuarioId") Long usuarioId, CreateOperacaoRequest operacaoRequest) {
        Set<ConstraintViolation<CreateOperacaoRequest>> violations = validator.validate(operacaoRequest);

        if (!violations.isEmpty()) {
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Operacao operacao = new Operacao();
        operacao.setNome(operacaoRequest.getNome());
        operacao.setDescricao(operacaoRequest.getDescricao());
        operacao.setCategoria(operacaoRequest.getCategoria());
        operacao.setAutenticacao(operacaoRequest.getAutenticacao());
        operacao.setPermissao(operacaoRequest.getPermissao());
        operacao.setUsuario(usuario);


        operacoesRepository.persist(operacao);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(operacao)
                .build();
    }

    @GET
    @Path("operacoes")
    public Response listarOperacoes() {
        PanacheQuery<Operacao> query = operacoesRepository.findAll();
        return Response.ok(query.list()).build();
    }

    @GET
    @Path("operacoes/{id}")
    public Response buscarOperacaoPorID(@PathParam("id") Long id) {
        Operacao operacao = operacoesRepository.findById(id);

        if (operacao != null) {
            return Response.ok(operacao).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("operacoes/nome")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        List<Operacao> operacao = operacoesRepository.findByNome(nome);

        if (operacao.isEmpty()) {
            throw new NotFoundException("Nenhuma operação com esse nome encontrado.");
        }

        return Response.ok(operacao).build();
    }

    @GET
    @Path("operacoes/descricao")
    public Response buscarPorDescricao(@QueryParam("descricao") String descricao) {
        List<Operacao> operacao = operacoesRepository.findByDescricao(descricao);

        if (operacao.isEmpty()) {
            throw new NotFoundException("Nenhuma operação com essa descrição encontrada.");
        }

        return Response.ok(operacao).build();
    }

    @GET
    @Path("operacoes/categoria")
    public Response buscarPorCategoria(@QueryParam("categoria") String categoria) {
        List<Operacao> operacao = operacoesRepository.findByCategoria(categoria);

        if (operacao.isEmpty()) {
            throw new NotFoundException("Nenhuma operação com essa categoria encontrada.");
        }

        return Response.ok(operacao).build();
    }

    @PUT
    @Transactional
    @Path("/usuarios/{usuarioId}/operacoes/{id}")
    public Response atualizarOperacao(@PathParam("id") Long id, @PathParam("usuarioId") Long usuarioId, CreateOperacaoRequest operacaoRequest) {

        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Operacao buscarOperacaoExistente = operacoesRepository.findById(id);
        if (buscarOperacaoExistente != null) {

            Operacao operacao = new Operacao();
            operacao.setNome(operacaoRequest.getNome());
            operacao.setDescricao(operacaoRequest.getDescricao());
            operacao.setCategoria(operacaoRequest.getCategoria());
            operacao.setAutenticacao(operacaoRequest.getAutenticacao());
            operacao.setPermissao(operacaoRequest.getPermissao());

            operacoesRepository.persist(operacao);
            return Response.ok(operacao).build();
        }


        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
