package com.listaVip.cadastro.service;

import com.listaVip.cadastro.model.*;
import com.listaVip.cadastro.repository.JWTTokenService;
import com.listaVip.cadastro.repository.SecurityConfig;
import com.listaVip.cadastro.repository.UserDetailsImpl;
import com.listaVip.cadastro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
    private SecurityConfig securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecuperarJwtTokenDto authenticateUser(LoginUsuariosDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecuperarJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CriarUsuariosDto createUserDto) {

        // Cria um novo usuário com os dados fornecidos
        Usuario newUser = Usuario.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .senha(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .papeisList(List.of(Papel.builder().nome(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}

