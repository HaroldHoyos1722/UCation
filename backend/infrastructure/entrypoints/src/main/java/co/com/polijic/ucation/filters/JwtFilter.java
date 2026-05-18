package co.com.polijic.ucation.filters;

import co.com.polijic.ucation.domain.common.SesionModel;
import co.com.polijic.ucation.domain.repositories.SesionRepositoryPort;
import co.com.polijic.ucation.usecases.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final List<String> urlsExcluidas;
    private final SesionRepositoryPort sesionRepositoryPort;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtFilter(JwtUtil jwtUtil, List<String> urlsExcluidas, SesionRepositoryPort sesionRepositoryPort) {
        this.jwtUtil = jwtUtil;
        this.urlsExcluidas = urlsExcluidas;
        this.sesionRepositoryPort = sesionRepositoryPort;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return urlsExcluidas.stream().anyMatch(patron -> pathMatcher.match(patron, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            escribirError(response, "Token no proporcionado");
            return;
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = jwtUtil.validarToken(token);
        } catch (JwtException e) {
            escribirError(response, "Token inválido o vencido");
            return;
        }

        SesionModel sesion = sesionRepositoryPort.findByCorreo(claims.getSubject());
        if (Objects.isNull(sesion) || !Objects.equals(sesion.getTokenSesion(), token)) {
            escribirError(response, "Sesión no válida o expirada");
            return;
        }

        chain.doFilter(request, response);
    }

    private void escribirError(HttpServletResponse response, String mensaje) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"mensaje\":\"" + mensaje + "\"}");
    }
}
