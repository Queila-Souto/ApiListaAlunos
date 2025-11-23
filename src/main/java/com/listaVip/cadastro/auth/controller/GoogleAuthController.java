package com.listaVip.cadastro.auth.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.listaVip.cadastro.auth.service.AuthService;
import com.listaVip.cadastro.security.detail.UserDetailsImpl;
import com.listaVip.cadastro.security.jwt.JWTTokenService;
import com.listaVip.cadastro.usuario.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador responsável pela autenticação de usuários utilizando o login do Google.
 *
 * <p>
 * Este controller recebe o ID Token gerado pelo Google Sign-In, valida sua autenticidade
 * por meio do {@link GoogleIdTokenVerifier} e, caso seja válido, gera ou recupera o usuário
 * associado ao e-mail contido no token. Em seguida, um token JWT da aplicação é gerado e
 * retornado ao cliente.
 * </p>
 *
 * <p>
 * Esse fluxo permite que o usuário acesse a aplicação sem necessidade de senha local,
 * utilizando exclusivamente as credenciais fornecidas pelo Google.
 * </p>
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticação Google",
        description = "Endpoints para autenticação de usuários utilizando Google Sign-In"
)
public class GoogleAuthController {

    private final GoogleIdTokenVerifier verifier;
    private final AuthService authService;
    private final JWTTokenService jwtTokenService;

    @PostMapping("/google")
    @Operation(
            summary = "Autenticar usuário via Google",
            description = "Recebe o ID Token do Google, valida sua autenticidade e retorna "
                    + "um token JWT interno da aplicação se o login for bem-sucedido.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticação realizada com sucesso",
                            content = @Content(
                                    schema = @Schema(
                                            description = "Objeto contendo o token JWT gerado e as informações básicas do usuário"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token inválido ou erro na autenticação",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao processar o login com Google",
                            content = @Content
                    )
            }
    )
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
            Usuario usuario = authService.findOrCreateByGoogle(email, name);

            // Cria UserDetailsImpl manualmente
            UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

            // Gera o JWT da API
            String token = jwtTokenService.generateToken(userDetails);

            return ResponseEntity.ok(
                    Map.of(
                            "token", token,
                            "nome", usuario.getNome(),
                            "email", usuario.getEmail()
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: " + e.getMessage());
        }
    }
}