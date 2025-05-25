package com.listaVip.cadastro.auth;

import com.listaVip.cadastro.usuario.entity.Usuario;
import com.listaVip.cadastro.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return new UserDetailsImpl(usuario);
    }

}