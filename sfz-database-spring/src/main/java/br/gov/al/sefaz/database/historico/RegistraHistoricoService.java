package br.gov.al.sefaz.database.historico;

import java.sql.Connection;

public interface RegistraHistoricoService {

    void registrar(Connection connection);

}
