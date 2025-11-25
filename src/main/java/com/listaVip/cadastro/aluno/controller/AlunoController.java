package com.listaVip.cadastro.aluno.controller;

import com.listaVip.cadastro.aluno.entity.Aluno;
import com.listaVip.cadastro.aluno.service.AlunoService;
import com.listaVip.cadastro.config.SecurityConfig;
import com.listaVip.cadastro.security.detail.UserDetailsImpl;
import com.listaVip.cadastro.usuario.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

/**
 * Controlador REST responsável por gerenciar operações relacionadas à entidade {@link Aluno}.
 *
 * Esta classe disponibiliza endpoints para:
 * <ul>
 *     <li>Cadastrar um novo aluno</li>
 *     <li>Listar todos os alunos</li>
 *     <li>Buscar um aluno específico por ID</li>
 *     <li>Atualizar informações de um aluno existente</li>
 *     <li>Remover um aluno do sistema</li>
 * </ul>
 *
 * A comunicação com a camada de serviço é feita através de {@link AlunoService},
 * que concentra as regras de negócio e persistência.
 *
 * Todos os métodos retornam respostas padronizadas utilizando {@link ResponseEntity}.
 */
@Tag(name = "Alunos", description = "Endpoints responsáveis pelo gerenciamento de alunos")
@RestController

@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Operation(
            summary = "Cadastra um novo aluno",
            description = "Recebe os dados de um aluno válido e o registra no sistema."
    )
    @PostMapping("/cadastro")
    public ResponseEntity<Aluno> cadastrar(@RequestBody @Valid Aluno aluno, @AuthenticationPrincipal UserDetailsImpl usuario ) {
        aluno.setUsuarioId(usuario.getId());
        Aluno salvo = alunoService.create(aluno);
        return ResponseEntity.created(URI.create("/alunos/" + salvo.getId())).body(salvo);
    }

    @Operation(
            summary = "Lista todos os alunos",
            description = "Retorna uma lista contendo todos os alunos cadastrados no sistema."
    )
    @GetMapping("/lista")
    public ResponseEntity<List<Aluno>> listar() {
        return ResponseEntity.ok(alunoService.findAll());
    }

    @GetMapping("/listafiltrada")
    public ResponseEntity<List<Aluno>> listarAlunosPorUsuario() {
        return ResponseEntity.ok(alunoService.findAlunoByUser());
    }

    @Operation(
            summary = "Busca aluno por ID",
            description = "Retorna os dados de um aluno específico com base no ID informado."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.findById(id));
    }

    @Operation(
            summary = "Atualiza um aluno",
            description = "Atualiza as informações de um aluno existente a partir do ID informado."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody @Valid Aluno aluno) {
        return ResponseEntity.ok(alunoService.update(id, aluno));
    }

    @Operation(
            summary = "Remove um aluno",
            description = "Remove um aluno do sistema com base no ID informado. Não retorna conteúdo."
    )
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
