package br.gov.al.sefaz.database.historico;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UsuarioAnonimoFornecedoridentidadeHistoricoDadosTest {

    UsuarioAnonimoFornecedoridentidadeHistoricoDados fornecedorIdentidade = new UsuarioAnonimoFornecedoridentidadeHistoricoDados();

    @Nested
    class AoPerguntarIsSessaoAtualAssociaUsuario {

        @Test
        void deveRetornarFalse() {

            assertThat(fornecedorIdentidade.isSessaoAtualAssociadaUsuario()).isFalse();

        }

    }

    @Nested
    class AoObterIdentidadeSessaoHistoricoDados {

        @Test
        void deveLancarExcecaoOperacaoNaoSuportada() {

            assertThatThrownBy(() -> fornecedorIdentidade.get()).isInstanceOf(UnsupportedOperationException.class);

        }

    }

}
