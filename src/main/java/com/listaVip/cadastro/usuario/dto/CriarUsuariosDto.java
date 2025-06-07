package com.listaVip.cadastro.usuario.dto;

import com.listaVip.cadastro.usuario.entity.Papeis;

public record CriarUsuariosDto(

        String nome,
        String email,
        String password

) {
}
