package com.listaVip.cadastro.auth;

import com.listaVip.cadastro.auth.dto.LoginUsuariosDto;
import com.listaVip.cadastro.usuario.dto.CriarUsuariosDto;
import com.listaVip.cadastro.auth.dto.RecuperarJwtTokenDto;
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

        @PostMapping("/login")
        public ResponseEntity<RecuperarJwtTokenDto> authenticateUser(@RequestBody LoginUsuariosDto loginUserDto) {
            RecuperarJwtTokenDto token = userService.authenticateUser(loginUserDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<Void> createUser(@RequestBody CriarUsuariosDto createUserDto) {
            userService.createUser(createUserDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @GetMapping("/test")
        public ResponseEntity<String> getAuthenticationTest() {
            return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
        }

        @GetMapping("/test/customer")
        public ResponseEntity<String> getCustomerAuthenticationTest() {
            return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
        }

        @GetMapping("/test/administrator")
        public ResponseEntity<String> getAdminAuthenticationTest() {
            return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
        }
    }

