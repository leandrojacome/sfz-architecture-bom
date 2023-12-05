package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.security.domain.UsuarioScaAutenticadoFixtures;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioScaAutenticadoFornecedorIdentidadeHistoricoDadosTest {

    @Mock AuthenticationContextService authenticationContextService;

    @InjectMocks UsuarioScaAutenticadoFornecedorIdentidadeHistoricoDados usuarioScaAutenticadoFornecedorIdentidadeHistoricoDados;

    @Nested
    class IsSessaoAtualAssociadaUsuario {

        @Test
        void deveriaPerguntarAoContextoSegurancaSeExisteUsuarioAutenticado() {
            usuarioScaAutenticadoFornecedorIdentidadeHistoricoDados.isSessaoAtualAssociadaUsuario();
            verify(authenticationContextService).temUsuarioScaAutenticado();
        }

        @Test
        void deveriaRetornarVerdadeiroSeContextoSegurancaPossuiUsuarioScaAutenticado() {
            when(authenticationContextService.temUsuarioScaAutenticado()).thenReturn(true);
            assertThat(usuarioScaAutenticadoFornecedorIdentidadeHistoricoDados.isSessaoAtualAssociadaUsuario()).isTrue();
        }

        @Test
        void deveriaRetornarFalsoSeContextoSegurancaPossuiUsuarioScaAutenticado() {
            when(authenticationContextService.temUsuarioScaAutenticado()).thenReturn(false);
            assertThat(usuarioScaAutenticadoFornecedorIdentidadeHistoricoDados.isSessaoAtualAssociadaUsuario()).isFalse();
        }

    }

    @Nested
    class Get {

        @Test
        void deveObterUsuarioScaDoContextoSeturanca() {

            when(authenticationContextService.getUsuarioScaAutenticadoDoContexto())
                    .thenReturn(UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido().build());

            usuarioScaAutenticadoFornecedorIdentidadeHistoricoDados.get();

            verify(authenticationContextService).getUsuarioScaAutenticadoDoContexto();

        }

        @Test
        void deveriaRetornarUmIdentidadeComDadosDoUsuarioNoContextoDeSeguranca() {

            var usuarioScaAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido().build();
            when(authenticationContextService.getUsuarioScaAutenticadoDoContexto()).thenReturn(usuarioScaAutenticado);

            var identidade = usuarioScaAutenticadoFornecedorIdentidadeHistoricoDados.get();

            assertThat(identidade).isNotNull();
            assertThat(identidade.numeroPessoa().longValue()).isEqualTo(usuarioScaAutenticado.numeroPessoa());
            assertThat(identidade.identificadorConexao()).isEqualTo(usuarioScaAutenticado.idenficadorConexao());

        }

    }

}
