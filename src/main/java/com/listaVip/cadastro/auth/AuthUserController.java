package com.listaVip.cadastro.auth;

import com.listaVip.cadastro.auth.dto.LoginUsuariosDto;
import com.listaVip.cadastro.auth.dto.RecuperarJwtTokenDto;
import com.listaVip.cadastro.auth.dto.RespostaAutenticacao;
import com.listaVip.cadastro.usuario.dto.CriarUsuariosDto;
import com.listaVip.cadastro.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class AuthUserController {

    @Autowired
    private UsuarioService userService;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;


    /**
     * Login tradicional (email + senha)
     */
    @PostMapping("/login")
    public ResponseEntity<RespostaAutenticacao> authenticateUser(@RequestBody LoginUsuariosDto loginUserDto) {
        RespostaAutenticacao response = userService.authenticateUser(loginUserDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Login via Google
     */
    @PostMapping("/google")
    public ResponseEntity<RespostaAutenticacao> loginComGoogle(@RequestBody RecuperarJwtTokenDto request) {

        try {
            // 1. Valida o token do Google
            var payload = googleTokenVerifier.verify(request.token());
            if (payload == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 2. Extrai dados do Google
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            // 3. Cria ou busca usuário no banco
            var usuario = userService.findOrCreateByGoogle(email, name);

            // 4. Gera JWT interno
            String token = jwtTokenService.generateToken(new UserDetailsImpl(usuario));

            // 5. Monta resposta
            RespostaAutenticacao response = new RespostaAutenticacao(token, usuario.getNome(), usuario.getEmail());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Cadastro de novo usuário
     */
    @PostMapping("/cadastro")
    public ResponseEntity<Void> createUser(@RequestBody CriarUsuariosDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Endpoints de teste
     */
    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return ResponseEntity.ok("Autenticado com sucesso");
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return ResponseEntity.ok("Cliente autenticado com sucesso");
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return ResponseEntity.ok("Administrador autenticado com sucesso");
    }
}
