package br.gov.al.sefaz.database.historico;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdentidadeSessaoHistoricoDadosTest {

    @Nested
    class AoConstruir {

        @Test
        void deveExigirUmNumeroPessoaNaoNulo() {
            assertThatThrownBy(() -> new IdentidadeSessaoHistoricoDados(null, "2"))
                    .hasMessage("numeroPessoa pessoa é requerido");
        }

        @Test
        void deveExigirUmIdentificadorConexaoNaoNulo() {
            assertThatThrownBy(() -> new IdentidadeSessaoHistoricoDados(1, null))
                    .hasMessage("identificadorConexao pessoa é requerido");
        }


    }

}
