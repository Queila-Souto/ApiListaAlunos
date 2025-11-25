package com.listaVip.cadastro.security.detail;

import com.listaVip.cadastro.usuario.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    public UserDetailsImpl(Usuario user) {
        this.usuario = user;
    }
    public Long getId() {
        return usuario.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
         Este método converte a lista de papéis (roles) associados ao usuário
         em uma coleção de GrantedAuthorities, que é a forma que o Spring Security
         usa para representar papéis. Isso é feito mapeando cada papel para um
         novo SimpleGrantedAuthority, que é uma implementação simples de
         GrantedAuthority
        */
        return usuario.getPapeisList()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    } // Retorna a credencial do usuário que criamos anteriormente

    @Override
    public String getUsername() {
        return usuario.getEmail();
    } // Retorna o nome de usuário do usuário que criamos anteriormente

}
