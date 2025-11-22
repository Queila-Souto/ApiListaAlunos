package com.listaVip.cadastro.auth.controller;

import com.listaVip.cadastro.auth.dto.LoginUsuariosDto;
import com.listaVip.cadastro.auth.dto.RecuperarJwtTokenDto;
import com.listaVip.cadastro.auth.dto.RespostaAutenticacaoDto;
import com.listaVip.cadastro.auth.service.AuthService;
import com.listaVip.cadastro.security.detail.UserDetailsImpl;
import com.listaVip.cadastro.security.google.GoogleTokenVerifier;
import com.listaVip.cadastro.security.jwt.JWTTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @PostMapping("/login")
    public ResponseEntity<RespostaAutenticacaoDto> authenticateUser(@RequestBody LoginUsuariosDto loginUserDto) {
        RespostaAutenticacaoDto response = authService.authenticateUser(loginUserDto);
        return ResponseEntity.ok(response);
    }

}
