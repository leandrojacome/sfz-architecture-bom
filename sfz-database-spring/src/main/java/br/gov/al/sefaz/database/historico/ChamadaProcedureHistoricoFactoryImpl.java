package br.gov.al.sefaz.database.historico;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;

public class ChamadaProcedureHistoricoFactoryImpl implements ChamadaProcedureHistoricoFactory {
    @Override
    public SimpleJdbcCallOperations criarParaConexao(Connection connection, String nomeProcedure) {
        return new SimpleJdbcCall(new SingleConnectionDataSource(connection, false))
                .withSchemaName("ADMHIS001")
                .withCatalogName("PC_HIS_ACESSO")
                .withProcedureName(nomeProcedure);
    }
}
