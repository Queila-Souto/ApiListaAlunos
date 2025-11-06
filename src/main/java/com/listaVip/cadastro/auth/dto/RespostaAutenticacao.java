package com.listaVip.cadastro.auth.dto;

public class RespostaAutenticacao {
    private String token;
    private String name;
    private String email;

    public RespostaAutenticacao(String token, String name, String email) {
        this.token = token;
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getToken() {
        return token;
    }
}
