package br.gov.al.sefaz.security.filter;

import br.gov.al.sefaz.security.domain.UsuarioScaAutenticado;
import br.gov.al.sefaz.security.service.AuthenticationContextService;
import br.gov.al.sefaz.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static br.gov.al.sefaz.security.domain.UsuarioScaAutenticado.StatusUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    public static final String TOKEN_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6" +
            "IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    public static final String HEADER_AUTORIZACAO_VALIDO = "Bearer " + TOKEN_JWT;

    @InjectMocks JwtAuthenticationFilter filter;
    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock FilterChain filterChain;
    @Mock JwtService jwtService;
    @Mock AuthenticationContextService authenticationContextService;

    @Nested
    class OnShouldNotFilter {

        // TODO criar cenários de lançamento de exceção na validação do token

        @Test
        void deveriaFiltrarCasoRecebeUmHeaderDeAutorizacaoValido() {
            when(request.getHeader("Authorization"))
                    .thenReturn(HEADER_AUTORIZACAO_VALIDO);
            assertThat(filter.shouldNotFilter(request)).isFalse();
        }

        @Test
        void naoDeveriaFiltrarCasoHeaderAutorizationNaoEstiverPresente() {
            when(request.getHeader("Authorization"))
                    .thenReturn(null);
            assertThat(filter.shouldNotFilter(request)).isTrue();
        }

        @Test
        void naoDeveriaFiltrarCasoHeaderAutorizacaoNaoForUmAuthorizationBearer() {
            when(request.getHeader("Authorization"))
                    .thenReturn("OutroHeader ");
            assertThat(filter.shouldNotFilter(request)).isTrue();
        }

    }

    @Nested
    class OnDoFilterInternal {

        @Test
        void deveVerificarSeTokenEstaValido() throws Exception {

            when(request.getHeader("Authorization"))
                    .thenReturn(HEADER_AUTORIZACAO_VALIDO);

            filter.doFilterInternal(request, response, filterChain);

            verify(jwtService).validateToken(TOKEN_JWT);

        }

        @Test
        void deveObterUsuarioAutenticacaoDoTokenQuandoTokenEstiverValido() throws Exception  {

            when(request.getHeader("Authorization"))
                    .thenReturn(HEADER_AUTORIZACAO_VALIDO);

            filter.doFilterInternal(request, response, filterChain);

            verify(jwtService).getAuthenticationUserFromToken(TOKEN_JWT);

        }

        @Test
        void deveriaProsseguirComChainCasoTokenEstiverInvalido() throws Exception {

            when(request.getHeader("Authorization"))
                    .thenReturn(HEADER_AUTORIZACAO_VALIDO);

            filter.doFilterInternal(request, response, filterChain);

            verify(filterChain).doFilter(same(request),same(response));

        }

        @Test
        void deveriaSetarUsuarioAutenticadoNoContextoDeSeguranca() throws Exception  {

            when(request.getHeader("Authorization"))
                    .thenReturn(HEADER_AUTORIZACAO_VALIDO);

            UsuarioScaAutenticado usuarioScaAutenticado = new UsuarioScaAutenticado(
                    1L, "2", "josesilva", StatusUsuario.ATIVO, Collections.emptyList(), null
            );

            when(jwtService.getAuthenticationUserFromToken(any())).thenReturn(usuarioScaAutenticado);

            filter.doFilterInternal(request, response, filterChain);

            verify(authenticationContextService).setContext(same(usuarioScaAutenticado));

        }

    }

}
