package com.listaVip.cadastro.aluno.repository;
import com.listaVip.cadastro.aluno.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {}
