package com.listaVip.cadastro.model;

public record CriarUsuariosDto(

        String email,
        String password,
        Papeis role

) {
}
