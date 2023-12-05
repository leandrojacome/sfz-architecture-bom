package br.gov.al.sefaz.database.historico;

import java.util.function.Supplier;

/**
 * Informa se a sessão atual de acesso a dados pode ser associada a um usuário e fornece a identeidade do usuário
 * associado.
 */
public interface FornecedorIdentidadeHistoricoDados extends Supplier<IdentidadeSessaoHistoricoDados> {

    /**
     * Se a atual sessão de interação com o banco de dados pode ser associada a uma identidade de usuário.
     * @return true se existe usuário na sessão para registro de dados.
     */
    boolean isSessaoAtualAssociadaUsuario();

}
