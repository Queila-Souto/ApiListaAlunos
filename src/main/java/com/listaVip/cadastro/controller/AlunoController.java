package com.listaVip.cadastro.controller;
import com.listaVip.cadastro.model.Aluno;
import com.listaVip.cadastro.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno pessoa) {
        Aluno salva = pessoaRepository.save(pessoa);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(pessoaRepository.findAll());
    }
}
