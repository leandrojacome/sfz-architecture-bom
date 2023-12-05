package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.security.configuration.SecurityProperties;
import br.gov.al.sefaz.security.domain.UsuarioScaAutenticado;
import br.gov.al.sefaz.security.exception.TokenJwtIncompletoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.List;

import static br.gov.al.sefaz.security.domain.UsuarioScaAutenticado.StatusUsuario;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

public class JwtServiceImpl implements JwtService {

    public static final String[] REQUIRED_CLAIMS = { "sub", "auth", "numPessoa", "idConexao", "indStatus" };

    private final SecurityProperties securityProperties;

    public JwtServiceImpl(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void validateToken(String jwtToken) {
        var parsedJwtClaims = parseJwtClaims(jwtToken);
        validateClaims(parsedJwtClaims);
    }

    private static void validateClaims(Claims claims) {
        for (String key : REQUIRED_CLAIMS) {
            if (!claims.containsKey(key)) {
                String mensagem = "A claim %s deveria estar presente no Token JWT.".formatted(key);
                throw new TokenJwtIncompletoException(mensagem);
            }
        }
    }

    @Override
    public UsuarioScaAutenticado getAuthenticationUserFromToken(String jwtToken) {
        var claims = parseJwtClaims(jwtToken);
        return createUsuarioScaSefazAutenticadoFromClaims(claims);
    }

    private static UsuarioScaAutenticado createUsuarioScaSefazAutenticadoFromClaims(Claims claims) {
        var indStatus = claims.get("indStatus", String.class).charAt(0);
        var permissoes = claims.get("auth", String.class).split(",");
        return new UsuarioScaAutenticado(claims.get("numPessoa", Long.class),
                                         claims.get("idConexao", String.class),
                                         claims.getSubject(),
                                         StatusUsuario.fromStatusChar(indStatus),
                                         List.of(permissoes),
                                         claims.get("matricula", Integer.class));
    }

    private Claims parseJwtClaims(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(hmacShaKeyFor(securityProperties.getJwt().getSecret().getBytes()))
                                                      .build()
                                                      .parseClaimsJws(jwtToken)
                                                      .getBody();
    }

}
