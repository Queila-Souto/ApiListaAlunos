package com.listaVip.cadastro.usuario.dto;

import com.listaVip.cadastro.usuario.entity.Papeis;

import java.util.List;

public record RecuperarUsuarioDto(

        Long id,
        String email,
        String nome,
        List<Papeis> papeisList

) {
}
