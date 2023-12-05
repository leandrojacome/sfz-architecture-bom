package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.security.domain.UsuarioScaAutenticado;

public interface JwtService {

    void validateToken(String jwtToken);

    UsuarioScaAutenticado getAuthenticationUserFromToken(String jwtToken);

}
