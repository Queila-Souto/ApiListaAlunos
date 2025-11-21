package com.listaVip.cadastro.usuario.repository;

import com.listaVip.cadastro.usuario.entity.Papeis;
import com.listaVip.cadastro.usuario.entity.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PapelRepository extends JpaRepository<Papel,String> {
    Optional<Papel> findByNome(Papeis nome);

}
