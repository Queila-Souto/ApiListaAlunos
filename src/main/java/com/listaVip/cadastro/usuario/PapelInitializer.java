package com.listaVip.cadastro.usuario;

import com.listaVip.cadastro.usuario.entity.Papeis;
import com.listaVip.cadastro.usuario.entity.Papel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PapelInitializer {
    private UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;

    public PapelInitializer(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    @PostConstruct
    public void initRoles() {
        for (Papeis papelEnum : Papeis.values()) {
            boolean exists = papelRepository.findByNome(papelEnum).isPresent();
            if (!exists) {
                papelRepository.save(Papel.builder().nome(papelEnum).build());
            }
        }
    }
}
