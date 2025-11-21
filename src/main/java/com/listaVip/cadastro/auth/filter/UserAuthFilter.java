package com.listaVip.cadastro.auth.filter;

import com.listaVip.cadastro.auth.JWTTokenService;
import com.listaVip.cadastro.auth.UserDetailsImpl;
import com.listaVip.cadastro.auth.config.SecurityConfig;
import com.listaVip.cadastro.usuario.UsuarioRepository;
import com.listaVip.cadastro.usuario.entity.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Se for endpoint público, apenas prossiga
            if (!requiresAuthentication(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = extractToken(request);

            if (token == null) {
                sendError(response, 401, "Token ausente.");
                return;
            }

            String email = jwtTokenService.getSubjectFromToken(token);

            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

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

            if (e.getMessage() != null) {

                // Token inválido/expirado
                if (e.getMessage().contains("JWT") || e.getMessage().contains("token")) {
                    sendError(response, 401, "Token inválido ou expirado.");
                    return;
                }
            }

            // Erro inesperado dentro do filtro → 401
            sendError(response, 401, "Não autorizado.");
        }
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .contains(uri);
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
