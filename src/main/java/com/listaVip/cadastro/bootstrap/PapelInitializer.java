package com.listaVip.cadastro.bootstrap;

import com.listaVip.cadastro.config.SecurityConfig;
import com.listaVip.cadastro.usuario.entity.Papeis;
import com.listaVip.cadastro.usuario.entity.Papel;
import com.listaVip.cadastro.usuario.entity.Usuario;
import com.listaVip.cadastro.usuario.repository.PapelRepository;
import com.listaVip.cadastro.usuario.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PapelInitializer {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PapelRepository papelRepository;
    @Autowired
    private SecurityConfig securityConfig;

    public PapelInitializer(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    @PostConstruct
    public void inicializar() {
        for (Papeis papelEnum : Papeis.values()) {
            boolean exists = papelRepository.findByNome(papelEnum).isPresent();
            if (!exists) {
                papelRepository.save(Papel.builder().nome(papelEnum).build());
            }
        }
        String emailAdmin = "admin@admin.com";
        String name = "User Admin";
        if (usuarioRepository.findByEmail(emailAdmin).isEmpty()) {
            Papel papelAdmin = papelRepository.findByNome(Papeis.PAPEL_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Papel ADMIN não encontrado"));

            Usuario admin = Usuario.builder()
                    .nome(name)
                    .email(emailAdmin)
                    .senha(securityConfig.passwordEncoder().encode("admin123"))
                    .papeisList(List.of(papelAdmin))
                    .build();

            usuarioRepository.save(admin);
        }
    }


}
