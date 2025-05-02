package com.listaVip.cadastro.repository;
import com.listaVip.cadastro.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {}
