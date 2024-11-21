package io.github.JuanLucca846.sistemaoperacoes.rest.resource;

import io.github.JuanLucca846.sistemaoperacoes.domain.model.Usuario;
import io.github.JuanLucca846.sistemaoperacoes.domain.repository.UsuarioRepository;
import io.github.JuanLucca846.sistemaoperacoes.rest.dto.CreateUsuarioRequest;
import io.github.JuanLucca846.sistemaoperacoes.rest.dto.ResponseError;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Path("/api/v1/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioRepository usuarioRepository;
    private final Validator validator;

    @Inject
    public UsuarioResource(UsuarioRepository usuarioRepository, Validator validator) {
        this.usuarioRepository = usuarioRepository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response criarUsuario(CreateUsuarioRequest usuarioRequest) {
        Set<ConstraintViolation<CreateUsuarioRequest>> violations = validator.validate(usuarioRequest);

        if (!violations.isEmpty()) {
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setIdade(usuarioRequest.getIdade());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(BcryptUtil.bcryptHash(usuarioRequest.getSenha()));


        usuarioRepository.persist(usuario);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("Mensagem", "Registrado com sucesso!");

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(resposta)
                .build();
    }
}
