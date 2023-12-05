package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.database.historico.FornecedorIdentidadeHistoricoDados;
import br.gov.al.sefaz.database.historico.IdentidadeSessaoHistoricoDados;

public class UsuarioScaAutenticadoFornecedorIdentidadeHistoricoDados implements FornecedorIdentidadeHistoricoDados {

    private final AuthenticationContextService authenticationContextService;

    public UsuarioScaAutenticadoFornecedorIdentidadeHistoricoDados(AuthenticationContextService authenticationContextService) {
        this.authenticationContextService = authenticationContextService;
    }

    @Override
    public boolean isSessaoAtualAssociadaUsuario() {
        return authenticationContextService.temUsuarioScaAutenticado();
    }

    @Override
    public IdentidadeSessaoHistoricoDados get() {
        var userSca = authenticationContextService.getUsuarioScaAutenticadoDoContexto();
        return new IdentidadeSessaoHistoricoDados(userSca.numeroPessoa().intValue(), userSca.idenficadorConexao());
    }

}
