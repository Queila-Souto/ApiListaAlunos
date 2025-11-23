package com.listaVip.cadastro.auth.controller;

import com.listaVip.cadastro.auth.dto.LoginUsuariosDto;
import com.listaVip.cadastro.auth.dto.RespostaAutenticacaoDto;
import com.listaVip.cadastro.auth.service.AuthService;
import com.listaVip.cadastro.security.google.GoogleTokenVerifier;
import com.listaVip.cadastro.security.jwt.JWTTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável pelos endpoints de autenticação de usuários de forma padrão, ou seja, utilizando login e senha.
 * <p>
 * Esta classe gerencia operações relacionadas ao processo de login,
 * incluindo validação das credenciais fornecidas e delegação para o serviço
 * de autenticação. Em caso de sucesso, um token JWT é retornado.
 * </p>
 */
@RestController
@RequestMapping("/usuario")
@Tag(
        name = "Autenticação Padrão",
        description = "Endpoints para autenticação de usuários"
)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuário - Autenticação Padrão",
            description = "Realiza a autenticação padrão de um usuário com login e senha. "
                    + "Retorna um token JWT em caso de sucesso.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticação realizada com sucesso",
                            content = @Content(
                                    schema = @Schema(implementation = RespostaAutenticacaoDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos ou requisição mal formatada",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciais inválidas",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<RespostaAutenticacaoDto> authenticateUser(
            @RequestBody LoginUsuariosDto loginUserDto
    ) {
        RespostaAutenticacaoDto response = authService.authenticateUser(loginUserDto);
        return ResponseEntity.ok(response);
    }
}
