package io.github.JuanLucca846.sistemaoperacoes.rest.resource;

import io.github.JuanLucca846.sistemaoperacoes.domain.model.Operacao;
import io.github.JuanLucca846.sistemaoperacoes.domain.repository.OperacoesRepository;
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

@Path("/api/v1/operacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OperacoesResource {

    private final OperacoesRepository operacoesRepository;
    private final Validator validator;

    @Inject
    public OperacoesResource(OperacoesRepository operacoesRepository, Validator validator) {
        this.operacoesRepository = operacoesRepository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response criarOperacao(CreateOperacaoRequest operacaoRequest) {
        Set<ConstraintViolation<CreateOperacaoRequest>> violations = validator.validate(operacaoRequest);

        if (!violations.isEmpty()) {
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        Operacao operacao = new Operacao();
        operacao.setNome(operacaoRequest.getNome());
        operacao.setDescricao(operacaoRequest.getDescricao());
        operacao.setCategoria(operacaoRequest.getCategoria());
        operacao.setRequisicao(operacaoRequest.getRequisicao());
        operacao.setResposta(operacaoRequest.getResposta());
        operacao.setAutenticacao(operacaoRequest.getAutenticacao());
        operacao.setPermissao(operacaoRequest.getPermissao());


        operacoesRepository.persist(operacao);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(operacao)
                .build();
    }

    @GET
    public Response listarOperacoes() {
        PanacheQuery<Operacao> query = operacoesRepository.findAll();
        return Response.ok(query.list()).build();
    }

    @GET
    @Path("{id}")
    public Response buscarOperacaoPorID(@PathParam("id") Long id) {
        Operacao operacao = operacoesRepository.findById(id);

        if (operacao != null) {
            return Response.ok(operacao).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/nome")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        List<Operacao> operacao = operacoesRepository.findByNome(nome);

        if (operacao.isEmpty()) {
            throw new NotFoundException("Nenhuma operação com esse nome encontrado.");
        }

        return Response.ok(operacao).build();
    }

    @GET
    @Path("/descricao")
    public Response buscarPorDescricao(@QueryParam("descricao") String descricao) {
        List<Operacao> operacao = operacoesRepository.findByDescricao(descricao);

        if (operacao.isEmpty()) {
            throw new NotFoundException("Nenhuma operação com essa descrição encontrada.");
        }

        return Response.ok(operacao).build();
    }

    @GET
    @Path("/categoria")
    public Response buscarPorCategoria(@QueryParam("categoria") String categoria) {
        List<Operacao> operacao = operacoesRepository.findByCategoria(categoria);

        if (operacao.isEmpty()) {
            throw new NotFoundException("Nenhuma operação com essa categoria encontrada.");
        }

        return Response.ok(operacao).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response atualizarOperacao(@PathParam("id") Long id, CreateOperacaoRequest operacaoRequest) {

        Operacao buscarOperacaoExistente = operacoesRepository.findById(id);

        if (buscarOperacaoExistente != null) {

            Operacao operacao = new Operacao();
            operacao.setNome(operacaoRequest.getNome());
            operacao.setDescricao(operacaoRequest.getDescricao());
            operacao.setCategoria(operacaoRequest.getCategoria());
            operacao.setRequisicao(operacaoRequest.getRequisicao());
            operacao.setResposta(operacaoRequest.getResposta());
            operacao.setAutenticacao(operacaoRequest.getAutenticacao());
            operacao.setPermissao(operacaoRequest.getPermissao());


            operacoesRepository.persist(operacao);
            return Response.ok(operacao).build();
        }


        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
