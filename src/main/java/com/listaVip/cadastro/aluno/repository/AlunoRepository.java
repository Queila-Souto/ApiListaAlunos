package com.listaVip.cadastro.aluno.repository;
import com.listaVip.cadastro.aluno.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByUsuarioId(Long usuarioId);
}
