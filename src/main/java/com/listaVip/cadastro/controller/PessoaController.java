package com.listaVip.cadastro.controller;
import com.listaVip.cadastro.model.Pessoa;
import com.listaVip.cadastro.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@RequestBody Pessoa pessoa) {
        Pessoa salva = pessoaRepository.save(pessoa);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(pessoaRepository.findAll());
    }
}
