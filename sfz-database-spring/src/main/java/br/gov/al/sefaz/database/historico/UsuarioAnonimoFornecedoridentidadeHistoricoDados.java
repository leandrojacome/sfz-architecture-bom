package br.gov.al.sefaz.database.historico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta implementação parte do princípio que não é possível haver usuário associado a alterações no banco de dados.
 * É indicada para serviços que não estimulados através de interações de usuários.
 */
public class UsuarioAnonimoFornecedoridentidadeHistoricoDados implements FornecedorIdentidadeHistoricoDados {

    private static final Logger log = LoggerFactory.getLogger(UsuarioAnonimoFornecedoridentidadeHistoricoDados.class);

    @Override
    public boolean isSessaoAtualAssociadaUsuario() {
        log.debug("Este acesso ao banco de dados não é associado a nenhum usuário final.");
        return false;
    }

    @Override
    public IdentidadeSessaoHistoricoDados get() {
        throw new UnsupportedOperationException();
    }

}
