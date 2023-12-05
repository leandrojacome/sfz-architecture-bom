package br.gov.al.sefaz.security.filter;

import br.gov.al.sefaz.security.service.AuthenticationContextService;
import br.gov.al.sefaz.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final AuthenticationContextService authenticationContextService;

    public JwtAuthenticationFilter(JwtService jwtService, AuthenticationContextService authenticationContextService) {
        this.jwtService = jwtService;
        this.authenticationContextService = authenticationContextService;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        return isNull(authHeader) || !(authHeader.startsWith("Bearer "));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.debug("Processando filtro de autenticação JWT.");
        var jwtToken = getJwtTokenFromAuthenticationHeader(request);
        log.debug("Processando filtro para o token JWT: {}", jwtToken);
        jwtService.validateToken(jwtToken);
        var usuarioScaSefazAutenticado = jwtService.getAuthenticationUserFromToken(jwtToken);
        log.debug("Adicionando usuario do SCA ao contexto de segurança: {}", usuarioScaSefazAutenticado);
        authenticationContextService.setContext(usuarioScaSefazAutenticado);
        filterChain.doFilter(request, response);
        log.debug("Concluído processamento do filtro.");
    }

    private static String getJwtTokenFromAuthenticationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization").replaceAll("Bearer ", "");
    }

}
