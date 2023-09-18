package br.gov.al.sefaz.database.historico;

import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;

import java.sql.Connection;

public interface ChamadaProcedureHistoricoFactory {

    SimpleJdbcCallOperations criarParaConexao(Connection connection, String nomeProcedure);

}
