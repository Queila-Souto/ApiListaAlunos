package com.listaVip.cadastro.security.filter;

import com.listaVip.cadastro.security.jwt.JWTTokenService;
import com.listaVip.cadastro.security.detail.UserDetailsImpl;
import com.listaVip.cadastro.usuario.repository.UsuarioRepository;
import com.listaVip.cadastro.usuario.entity.Usuario;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    private static final AntPathRequestMatcher[] PUBLIC_MATCHERS = {
            new AntPathRequestMatcher("/usuario/login"),
            new AntPathRequestMatcher("/usuario/cadastro"),
            new AntPathRequestMatcher("/auth/google"),
            new AntPathRequestMatcher("/v3/api-docs"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-resources"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/webjars/**")
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("🔎 URI no filtro: " + request.getRequestURI());

        // Se a rota é pública, ignora o filtro
        if (isPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);
            if (token == null) {
                sendError(response, 401, "Token ausente.");
                return;
            }

            // ⬅️ Agora pegamos o ID DO TOKEN
            Long userId = jwtTokenService.getUserIdFromToken(token);
            System.out.println("🔑 userId extraído do token: " + userId);

            Usuario usuario = usuarioRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado pelo ID."));

            UserDetails userDetails = new UserDetailsImpl(usuario);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);

        } catch (RuntimeException e) {
            System.out.println("❌ Erro no filtro: " + e.getMessage());
            sendError(response, 401, "Token inválido ou expirado.");
        }
    }


    private boolean isPublic(HttpServletRequest request) {
        for (AntPathRequestMatcher matcher : PUBLIC_MATCHERS) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    private String extractToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7);
        }
        return null;
    }

    private void sendError(HttpServletResponse response, int status, String message)
            throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("""
                {
                  "erro": "%s"
                }
                """.formatted(message));
    }
}
