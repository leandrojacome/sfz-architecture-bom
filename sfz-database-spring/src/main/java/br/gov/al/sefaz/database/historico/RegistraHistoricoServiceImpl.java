package br.gov.al.sefaz.database.historico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class RegistraHistoricoServiceImpl implements RegistraHistoricoService {

    private final Logger log = LoggerFactory.getLogger(RegistraHistoricoService.class);

    private final FornecedorIdentidadeHistoricoDados fornecedoridentidadeHistoricoDados;
    private final ChamadaProcedureHistoricoFactory procedureHistoricoFactory;

    public RegistraHistoricoServiceImpl(FornecedorIdentidadeHistoricoDados fornecedoridentidadeHistoricoDados,
                                        ChamadaProcedureHistoricoFactory chamadaProcedureHistoricoFactory) {
        this.fornecedoridentidadeHistoricoDados = fornecedoridentidadeHistoricoDados;
        this.procedureHistoricoFactory = chamadaProcedureHistoricoFactory;
    }

    @Override
    public void registrar(Connection connection) {
        liberarSessaoHistorico(connection);
        if (fornecedoridentidadeHistoricoDados.isSessaoAtualAssociaUsuario()) {
            var identidade = fornecedoridentidadeHistoricoDados.get();
            log.debug("Registrando sessão de histórico para {}.", identidade);
            identificarSessaoHistorico(connection, identidade);
            log.debug("Registro de sessão de histórico para {} concluído.", identidade);
        }
    }

    private void identificarSessaoHistorico(Connection connection, IdentidadeSessaoHistoricoDados identidade) {
        procedureHistoricoFactory.criarParaConexao(connection, "SP_IDENTIFICA_SESSAO")
                .execute(identidade.numeroPessoa(), identidade.identificadorConexao());
    }

    private void liberarSessaoHistorico(Connection connection) {
        procedureHistoricoFactory.criarParaConexao(connection, "SP_LIBERA_SESSAO").execute();
    }

}
