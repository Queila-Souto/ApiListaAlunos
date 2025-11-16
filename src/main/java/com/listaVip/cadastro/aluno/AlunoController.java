package com.listaVip.cadastro.aluno;

import com.listaVip.cadastro.aluno.entity.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping ("/cadastro")
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno pessoa) {
        Aluno salva = alunoRepository.save(pessoa);
        return ResponseEntity.ok(salva);
    }

    @GetMapping ("/lista")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(alunoRepository.findAll());
    }


    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {


        Optional<Aluno> alunoOptional = alunoRepository.findById(id);

        if (alunoOptional.isPresent()) {
            alunoRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 - Sucesso sem conteúdo
        } else {
            return ResponseEntity.notFound().build(); // 404 - Aluno não encontrado
        }
    }
}
