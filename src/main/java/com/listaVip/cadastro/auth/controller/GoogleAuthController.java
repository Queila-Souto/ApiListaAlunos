package com.listaVip.cadastro.auth.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.listaVip.cadastro.auth.service.AuthService;
import com.listaVip.cadastro.security.detail.UserDetailsImpl;
import com.listaVip.cadastro.security.jwt.JWTTokenService;
import com.listaVip.cadastro.usuario.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class GoogleAuthController {

    private final GoogleIdTokenVerifier verifier;
    private final AuthService authService;
    private final JWTTokenService jwtTokenService;

    @PostMapping("/google")
    public ResponseEntity<?> loginComGoogle(@RequestBody Map<String, String> body) {
        String idTokenString = body.get("idToken");

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            // Cria ou busca o usuário
            Usuario usuario = authService.findOrCreateByGoogle(email, name );

            // 🔹 2. Cria UserDetailsImpl “na mão”
            UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

            // Gera o JWT da sua API
            String token = jwtTokenService.generateToken(userDetails);

            return ResponseEntity.ok(Map.of("token", token,"nome",usuario.getNome(),"email",usuario.getEmail()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: " + e.getMessage());
        }
    }
}
