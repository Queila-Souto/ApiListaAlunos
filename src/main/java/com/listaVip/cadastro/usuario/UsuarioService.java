package com.listaVip.cadastro.usuario;

import com.listaVip.cadastro.auth.JWTTokenService;
import com.listaVip.cadastro.usuario.dto.CriarUsuariosDto;
import com.listaVip.cadastro.auth.dto.LoginUsuariosDto;
import com.listaVip.cadastro.auth.dto.RecuperarJwtTokenDto;
import com.listaVip.cadastro.usuario.entity.Papeis;
import com.listaVip.cadastro.usuario.entity.Papel;
import com.listaVip.cadastro.usuario.entity.Usuario;
import com.listaVip.cadastro.auth.config.SecurityConfig;
import com.listaVip.cadastro.auth.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        Papel papel = papelRepository.findByNome(Papeis.PAPEL_CLIENTE)
                .orElseThrow(() -> new RuntimeException("Papel 'PAPEL_CLIENTE' não encontrado no banco"));

        // Cria um novo usuário com os dados fornecidos
        Usuario newUser = Usuario.builder()
                .nome(createUserDto.nome())
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .senha(securityConfiguration.passwordEncoder().encode(createUserDto.senha()))
                // Atribui ao usuário uma permissão específica
                .papeisList(List.of(papel))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}

