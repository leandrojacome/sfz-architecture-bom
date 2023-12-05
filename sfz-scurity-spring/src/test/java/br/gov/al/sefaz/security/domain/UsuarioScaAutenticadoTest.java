package br.gov.al.sefaz.security.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static br.gov.al.sefaz.security.domain.UsuarioScaAutenticado.StatusUsuario;
import static org.assertj.core.api.Assertions.*;

class UsuarioScaAutenticadoTest {

    @Nested
    class AoCriar {

        @Test
        void deveriaRequererNumeroPessoa() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .numeroPessoa(null);
            assertThatThrownBy(usuarioAutenticado::build)
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void deveriaRequererIdentificadorConexao() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .idenficadorConexao(null);
            assertThatThrownBy(usuarioAutenticado::build)
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void deveriaRequererLogin() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .login(null);
            assertThatThrownBy(usuarioAutenticado::build)
                    .isInstanceOf(NullPointerException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  "})
        void deveriaValidarLoginVazio(String login) {

            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .login(login);
            assertThatThrownBy(usuarioAutenticado::build)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Login não pode ser vazio");
        }

        @Test
        void deveriaRequererStatus() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .status(null);
            assertThatThrownBy(usuarioAutenticado::build)
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void deveriaInicializarPermissoesComListaVaziaCasoInformadoNulo() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .permissoes(null)
                                                                  .build();
            assertThat(usuarioAutenticado.permissoes()).isEmpty();
        }

        @Test
        void deveriaCopiarExatamenteTodosElementosInformadosDePermissoes() {
            var permissoes = criarUmaListaMutavelComPermissoes();
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .permissoes(permissoes)
                                                                  .build();
            assertThat(usuarioAutenticado.permissoes())
                    .containsAll(permissoes)
                    .hasSize(permissoes.size());
        }

        @Test
        void deveriaCopiarElementosDaListaDePermissoesParaUmaNova() {
            var permissoes = criarUmaListaMutavelComPermissoes();
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .permissoes(permissoes)
                                                                  .build();
            assertThat(usuarioAutenticado.permissoes()).isNotSameAs(permissoes);
        }

        @Test
        void listaDePermissoesDeveSerImutavel() {
            List<String> permissoes = criarUmaListaMutavelComPermissoes();
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido()
                                                                  .permissoes(permissoes)
                                                                  .build();
            assertThatThrownBy(() -> usuarioAutenticado.permissoes().add("teste"));
        }

        private static List<String> criarUmaListaMutavelComPermissoes() {
            List<String> permissoes = new ArrayList<>();
            permissoes.add("permissao_a");
            permissoes.add("permissao_b");
            permissoes.add("permissao_c");
            return permissoes;
        }

    }

    @Nested
    class AoObterStatusUsuarioDoStatusChar {

        @Test
        void deveriaRetornarStatusCorrespondente() {
            assertThat(StatusUsuario.fromStatusChar('T')).isEqualTo(StatusUsuario.TROCAR_SENHA);
        }

        @Test
        void deveriaLancarExcecaoCasoCaractereCorrespondenteNaoForEncontrado() {
            assertThatThrownBy(() -> StatusUsuario.fromStatusChar('W'))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

}
