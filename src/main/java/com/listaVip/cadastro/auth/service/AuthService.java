package com.listaVip.cadastro.auth.service;

import com.listaVip.cadastro.auth.dto.LoginUsuariosDto;
import com.listaVip.cadastro.auth.dto.RespostaAutenticacaoDto;
import com.listaVip.cadastro.config.SecurityConfig;
import com.listaVip.cadastro.security.detail.UserDetailsImpl;
import com.listaVip.cadastro.security.jwt.JWTTokenService;
import com.listaVip.cadastro.usuario.entity.Usuario;
import com.listaVip.cadastro.usuario.repository.PapelRepository;
import com.listaVip.cadastro.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

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

    public RespostaAutenticacaoDto authenticateUser(LoginUsuariosDto loginUserDto) {
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
        return new RespostaAutenticacaoDto(token, usuario.getNome(), usuario.getEmail());
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
