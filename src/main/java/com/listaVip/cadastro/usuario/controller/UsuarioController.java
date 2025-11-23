package com.listaVip.cadastro.usuario.controller;

import com.listaVip.cadastro.usuario.dto.CriarUsuariosDto;
import com.listaVip.cadastro.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável pela criação de novos usuários.
 *
 * <p>
 * Este controller expõe o endpoint de cadastro, recebendo os dados necessários
 * para criar um novo usuário no sistema e delegando o processo ao {@link UsuarioService}.
 * </p>
 *
 * <p>
 * Após o cadastro bem-sucedido, a API retorna o status HTTP 201 (Created),
 * sem corpo de resposta.
 * </p>
 */
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(
        name = "Usuários",
        description = "Endpoints relacionados ao gerenciamento de usuários"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário com os dados fornecidos. "
                    + "Retorna o status 201 em caso de sucesso.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário criado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos ou requisição mal formatada",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Void> createUser(@RequestBody CriarUsuariosDto createUserDto) {
        usuarioService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}