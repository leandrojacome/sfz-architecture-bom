package br.gov.al.sefaz.database.historico;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;

import java.sql.Connection;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistraHistoricoServiceImplTest {

    @InjectMocks RegistraHistoricoServiceImpl service;

    @Mock FornecedorIdentidadeHistoricoDados fornecedoridentidadeHistoricoDados;
    @Mock ChamadaProcedureHistoricoFactory chamadaProcedureHistoricoFactory;
    @Mock Connection connection;
    @Mock SimpleJdbcCallOperations procedureLiberarSessao;
    @Mock SimpleJdbcCallOperations procedureIdentificaSessao;

    @Nested
    class AoRegistrar {

        @Test
        void deveExecutarProcedureLiberaSessao() {

            stubSessaoAtualAssociadoUsuario(true);
            subCriacaoProcedures();
            stubGetIdentidadeHistoricoDados(1, 2);

            service.registrar(connection);

            verify(procedureLiberarSessao).execute();

        }

        @Test
        void deveExecutarProcedureLiberaSessaoMesmoSemUsuarioNaSessao() {

            stubSessaoAtualAssociadoUsuario(false);
            stubCriarProcedureLiberarSessao();

            service.registrar(connection);

            verify(procedureLiberarSessao).execute();

        }

        @Test
        void deveExecutarProcedureIdentificaSessao() {

            final var numeroPessoa = 11;
            final var identificadorConexao = 12;

            stubSessaoAtualAssociadoUsuario(true);
            subCriacaoProcedures();
            stubGetIdentidadeHistoricoDados(numeroPessoa, identificadorConexao);

            service.registrar(connection);

            verify(procedureIdentificaSessao).execute(numeroPessoa, identificadorConexao);

        }

        @Test
        void deveExecutarProcedureLiberaSessaoAntesDeIdentificarSessao() {

            stubSessaoAtualAssociadoUsuario(true);
            subCriacaoProcedures();
            stubGetIdentidadeHistoricoDados(1, 2);

            var inOrder = inOrder(procedureLiberarSessao, procedureIdentificaSessao);

            service.registrar(connection);

            inOrder.verify(procedureLiberarSessao).execute();
            inOrder.verify(procedureIdentificaSessao).execute(any(), any());

        }

        private void stubGetIdentidadeHistoricoDados(int numeroPessoa, int identificadorConexao) {
            when(fornecedoridentidadeHistoricoDados.get())
                    .thenReturn(umaIdentidadeHistorico(numeroPessoa, identificadorConexao));
        }

        private void stubSessaoAtualAssociadoUsuario(boolean isAssociado) {
            when(fornecedoridentidadeHistoricoDados.isSessaoAtualAssociaUsuario())
                    .thenReturn(isAssociado);
        }

        private void subCriacaoProcedures() {
            stubCriarProcedureLiberarSessao();
            when(chamadaProcedureHistoricoFactory.criarParaConexao(same(connection), eq("SP_IDENTIFICA_SESSAO")))
                    .thenReturn(procedureIdentificaSessao);
        }

        private void stubCriarProcedureLiberarSessao() {
            when(chamadaProcedureHistoricoFactory.criarParaConexao(same(connection), eq("SP_LIBERA_SESSAO")))
                    .thenReturn(procedureLiberarSessao);
        }

    }

    private static IdentidadeSessaoHistoricoDados umaIdentidadeHistorico(int numeroPessoa, int identificadorConexao) {
        return new IdentidadeSessaoHistoricoDados(numeroPessoa, identificadorConexao);
    }

}
