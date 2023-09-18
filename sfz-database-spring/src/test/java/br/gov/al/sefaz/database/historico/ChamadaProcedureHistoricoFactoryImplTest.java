package br.gov.al.sefaz.database.historico;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ChamadaProcedureHistoricoFactoryImplTest {

    @InjectMocks ChamadaProcedureHistoricoFactoryImpl factory;

    @Mock Connection connection;

    @Nested
    class AoCriarParaConexao {

        @Test
        void deveriaCriarComSchemaAdmHis001() {
            var resultado = (SimpleJdbcCall) factory.criarParaConexao(connection, "PROCEDURE");
            assertThat(resultado.getSchemaName()).isEqualTo("ADMHIS001");
        }

        @Test
        void deveriaCriarComPacotePcHisAcesso() {
            var resultado = (SimpleJdbcCall) factory.criarParaConexao(connection, "PROCEDURE");
            assertThat(resultado.getCatalogName()).isEqualTo("PC_HIS_ACESSO");
        }

        @Test
        void deveriaCriarNomeProcedureInformado() {
            var resultado = (SimpleJdbcCall) factory.criarParaConexao(connection, "SP_REGISTRA_HISTORICO");
            assertThat(resultado.getProcedureName()).isEqualTo("SP_REGISTRA_HISTORICO");
        }

        @Test
        void deveriaCriarUmaSimpleJdbcCallComUmDatasourceParaConexaoPassada() throws SQLException {
            var resultado = (SimpleJdbcCall) factory.criarParaConexao(connection, "ALGUMA_PROCEDURE");
            var dataSource = resultado.getJdbcTemplate().getDataSource();
            assertThat(dataSource).isInstanceOf(SingleConnectionDataSource.class);
            assertThat(dataSource.getConnection())
                    .isNotNull()
                    .isSameAs(connection);
        }

    }


}
