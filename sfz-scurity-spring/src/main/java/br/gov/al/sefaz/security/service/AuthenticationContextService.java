package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.security.domain.UsuarioScaAutenticado;

public interface AuthenticationContextService {

    void setContext(UsuarioScaAutenticado usuarioAutenticado);

    boolean temUsuarioScaAutenticado();

    UsuarioScaAutenticado getUsuarioScaAutenticadoDoContexto();

}
