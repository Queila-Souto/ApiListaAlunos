package com.listaVip.cadastro.usuario;

import com.listaVip.cadastro.auth.JWTTokenService;
import com.listaVip.cadastro.auth.dto.RespostaAutenticacao;
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
    public RespostaAutenticacao authenticateUser(LoginUsuariosDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera o token JWT
        String token = jwtTokenService.generateToken(userDetails);

        // Busca o usuário completo no banco para pegar nome e email
        Usuario usuario = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // Retorna token + dados do usuário
        return new RespostaAutenticacao(token, usuario.getNome(), usuario.getEmail());
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

    public Usuario findOrCreateByGoogle(String email, String name) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario user = new Usuario();
                    user.setEmail(email);
                    user.setNome(name);
                    return userRepository.save(user);
                });
    }
}

