package com.listaVip.cadastro.usuario.dto;

import com.listaVip.cadastro.usuario.entity.Papeis;

public record CriarUsuariosDto(

        String email,
        String password,
        Papeis role

) {
}
