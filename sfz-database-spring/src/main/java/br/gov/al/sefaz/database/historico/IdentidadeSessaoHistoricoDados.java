package br.gov.al.sefaz.database.historico;

import java.util.Objects;

/**
 * Representa uma identidade que pode ser identificada como autor de alterações nos dados para registro de histórico.
 */
public record IdentidadeSessaoHistoricoDados(Integer numeroPessoa, String identificadorConexao) {

    /**
     * @param numeroPessoa - identificador da pessoa no modelo de dados da SEFAZ
     * @param identificadorConexao - identificador da conexão que identifica a autenticação da identidade (geralmente
     *                              idenficador conexão do SCA)
     */
    public IdentidadeSessaoHistoricoDados {
        Objects.requireNonNull(numeroPessoa, "numeroPessoa pessoa é requerido");
        Objects.requireNonNull(identificadorConexao, "identificadorConexao pessoa é requerido");
    }

    @Override
    public String toString() {
        return "IdentidadeSessaoHistoricoDados{" +
                "numeroPessoa=" + numeroPessoa +
                ", identificadorConexao=" + identificadorConexao +
                '}';
    }
}
