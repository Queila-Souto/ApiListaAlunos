package com.listaVip.cadastro.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- 1) TRATA O EMAIL DUPLICADO NO CÓDIGO (RuntimeException) ---
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {

        if ("EMAIL_DUPLICADO".equals(ex.getMessage())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409
                    .body(Map.of("erro", "O e-mail informado já está em uso"));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // ou 500, se preferir
                .body(Map.of("erro", ex.getMessage()));
    }


    // --- 2) TRATA EMAIL DUPLICADO LANÇADO PELO BANCO (UNIQUE CONSTRAINT) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleIntegrity(DataIntegrityViolationException ex) {

        String mensagem = ex.getMostSpecificCause().getMessage().toLowerCase();

        if (mensagem.contains("duplicate") || mensagem.contains("unique")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "O e-mail informado já está cadastrado"));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", "Erro de integridade de dados", "detalhe", mensagem));
    }
}
