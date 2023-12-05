package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.security.domain.UsuarioScaAutenticado;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class SpringSecurityAuthenticationContextService implements AuthenticationContextService {

    @Override
    public void setContext(UsuarioScaAutenticado usuarioAutenticado) {
        var authentication = criarAutentication(usuarioAutenticado);
        setarAuthenticacaoNoContexto(authentication);
    }

    @Override
    public boolean temUsuarioScaAutenticado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (possuiAuthenticationMaisPrincipal(authentication)) {
            return false;
        }
        return authentication.getPrincipal() instanceof UsuarioScaAutenticado;
    }

    private static boolean possuiAuthenticationMaisPrincipal(Authentication authentication) {
        return isNull(authentication) || isNull(authentication.getPrincipal());
    }

    @Override
    public UsuarioScaAutenticado getUsuarioScaAutenticadoDoContexto() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNull(authentication)) {
            return null;
        }
        return (UsuarioScaAutenticado) authentication.getPrincipal();
    }

    private static UsernamePasswordAuthenticationToken criarAutentication(UsuarioScaAutenticado usuarioAutenticado) {
        var authorities = usuarioAutenticado.permissoes()
                                            .stream()
                                            .map(SimpleGrantedAuthority::new)
                                            .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(usuarioAutenticado, null, authorities);
    }

    private static void setarAuthenticacaoNoContexto(UsernamePasswordAuthenticationToken authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
