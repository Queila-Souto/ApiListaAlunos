package com.listaVip.cadastro.model;

import java.util.List;

public record RecuperarUsuarioDto(

        Long id,
        String email,
        List<Papeis> papeisList

) {
}
