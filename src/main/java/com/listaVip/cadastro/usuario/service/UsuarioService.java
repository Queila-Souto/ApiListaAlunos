package com.listaVip.cadastro.usuario.service;

import com.listaVip.cadastro.security.jwt.JWTTokenService;
import com.listaVip.cadastro.usuario.repository.PapelRepository;
import com.listaVip.cadastro.usuario.repository.UsuarioRepository;
import com.listaVip.cadastro.usuario.dto.CriarUsuariosDto;
import com.listaVip.cadastro.usuario.entity.Papeis;
import com.listaVip.cadastro.usuario.entity.Papel;
import com.listaVip.cadastro.usuario.entity.Usuario;
import com.listaVip.cadastro.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PapelRepository papelRepository;

    @Autowired
    private SecurityConfig securityConfiguration;


    public void createUser(CriarUsuariosDto dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("EMAIL_DUPLICADO");
        }

        Papel papel = papelRepository.findByNome(Papeis.PAPEL_CLIENTE)
                .orElseThrow(() -> new RuntimeException("Papel 'PAPEL_CLIENTE' não encontrado"));

        Usuario newUser = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(securityConfiguration.passwordEncoder().encode(dto.senha()))
                .papeisList(List.of(papel))
                .build();

        userRepository.save(newUser);
    }

}

