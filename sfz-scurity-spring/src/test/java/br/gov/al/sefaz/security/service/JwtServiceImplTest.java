package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.security.JwtTokenFixtures;
import br.gov.al.sefaz.security.configuration.SecurityProperties;
import br.gov.al.sefaz.security.exception.TokenJwtIncompletoException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static br.gov.al.sefaz.security.domain.UsuarioScaAutenticado.StatusUsuario;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @InjectMocks JwtServiceImpl jwtService;

    @Mock SecurityProperties securityProperties;
    @Mock SecurityProperties.Jwt jwt;

    @Nested
    class OnValidateToken {

        @Test
        void deveriaLancarExcecaoQuandoTokenEstiverExpirado() {

            when(securityProperties.getJwt()).thenReturn(jwt);
            when(jwt.getSecret()).thenReturn(JwtTokenFixtures.JWT_CHAVE_VALIDA);

            var token = JwtTokenFixtures.createInvalidToken();

            assertThatThrownBy(() -> jwtService.validateToken(token)).isInstanceOf(ExpiredJwtException.class);

        }

        @Test
        void naoDeveriaLancarExcecaoQuandoTokenEstiverValido() {

            when(securityProperties.getJwt()).thenReturn(jwt);
            when(jwt.getSecret()).thenReturn(JwtTokenFixtures.JWT_CHAVE_VALIDA);

            var token = JwtTokenFixtures.createValidToken();

            assertDoesNotThrow(() -> jwtService.validateToken(token));

        }

        @Test
        void deveriaLancarExcecaoCasoAssinaturaNaoForValida() {

            when(securityProperties.getJwt()).thenReturn(jwt);
            when(jwt.getSecret()).thenReturn(JwtTokenFixtures.JWT_CHAVE_INVALIDA);

            var token = JwtTokenFixtures.createValidToken();

            assertThatThrownBy(() -> jwtService.validateToken(token)).isInstanceOf(SignatureException.class);

        }

        @ParameterizedTest
        @ValueSource(strings = {"sub", "auth", "numPessoa", "idConexao", "indStatus"})
        void deveriaLancarExcecaoCasoClaimsRequeridasNaoEstiveremPresentesNoToken(String chaveNaoPresente) {

            when(securityProperties.getJwt()).thenReturn(jwt);
            when(jwt.getSecret()).thenReturn(JwtTokenFixtures.JWT_CHAVE_VALIDA);

            Map<String, Object> claims = new HashMap<>(Map.of(
                    "sub", "login",
                    "auth", "ROLE_01,ROLE_02",
                    "numPessoa", 3,
                    "idConexao", "1a4f25e9b",
                    "indStatus", "A"
            ));
            claims.remove(chaveNaoPresente);

            var token = JwtTokenFixtures.createTokenWithClaims(claims);

            assertThatThrownBy(() -> jwtService.validateToken(token))
                    .isInstanceOf(TokenJwtIncompletoException.class)
                    .hasMessage("A claim %s deveria estar presente no Token JWT.".formatted(chaveNaoPresente));

        }

    }

    @Nested
    class OnGetAuthenticationUserFromToken {

        @Test
        void deveriaCriarUmUsuarioAutenticadoValido() {

            when(securityProperties.getJwt()).thenReturn(jwt);
            when(jwt.getSecret()).thenReturn(JwtTokenFixtures.JWT_CHAVE_VALIDA);

            var resultado = jwtService.getAuthenticationUserFromToken(JwtTokenFixtures.createValidToken());

            assertThat(resultado.numeroPessoa()).isEqualTo(JwtTokenFixtures.JWT_CLAIM_NUMERO_PESSOA);
            assertThat(resultado.idenficadorConexao()).isEqualTo(JwtTokenFixtures.JWT_CLAIM_ID_CONEXAO);
            assertThat(resultado.login()).isEqualTo(JwtTokenFixtures.JWT_CLAIM_SUBJECT);
            assertThat(resultado.status()).isEqualTo(StatusUsuario.ATIVO);
            assertThat(resultado.matricula()).isEqualTo(JwtTokenFixtures.JWT_CLAIM_MATRICULA);
            assertThat(resultado.permissoes()).hasSize(3).contains("ROLE_A", "ROLE_B", "ROLE_C");

        }

        //TODO COBRIR CENÁRIOS COM ESPAÇOS A MAIS NO STRING DE PERMISSÕES

    }

}
