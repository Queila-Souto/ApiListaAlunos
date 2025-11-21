package com.listaVip.cadastro.aluno.controller;

import com.listaVip.cadastro.aluno.entity.Aluno;
import com.listaVip.cadastro.aluno.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Aluno> cadastrar(@RequestBody @Valid Aluno aluno) {
        Aluno salvo = alunoService.create(aluno);
        return ResponseEntity.created(URI.create("/alunos/" + salvo.getId())).body(salvo);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Aluno>> listar() {
        return ResponseEntity.ok(alunoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody @Valid Aluno aluno) {
        return ResponseEntity.ok(alunoService.update(id, aluno));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}