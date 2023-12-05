package br.gov.al.sefaz.security.service;

import br.gov.al.sefaz.security.domain.UsuarioScaAutenticadoFixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static br.gov.al.sefaz.security.domain.UsuarioScaAutenticadoFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

class SpringSecurityAuthenticationContextServiceTest {

    SpringSecurityAuthenticationContextService service = new SpringSecurityAuthenticationContextService();

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    class SetContext {

        @Test
        void deveriaSetarUmaAtuenticacaoDoTipoUserNamePasswordAuthenticationToken() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido().build();
            service.setContext(usuarioAutenticado);
            assertThat(SecurityContextHolder.getContext().getAuthentication())
                    .isNotNull()
                    .isInstanceOf(UsernamePasswordAuthenticationToken.class);
        }

        @Test
        void deveriaSetarUsuarioAutenticadoComoPrincipal() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido().build();
            service.setContext(usuarioAutenticado);
            assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .isSameAs(usuarioAutenticado);
        }

        @Test
        void deveriaConverterSetarPermissoesDoUsuarioAutenticadoComoGrantedAuthoritiesNaAutenticacao() {
            var usuarioAutenticado = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido().build();
            service.setContext(usuarioAutenticado);
            @SuppressWarnings("unchecked")
            var authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
                                                                                  .getAuthentication()
                                                                                  .getAuthorities();
            assertThat(authorities).containsOnly(umaGrantedAuthority(USUARIO_VALIDO_ROLE_01),
                                                 umaGrantedAuthority(USUARIO_VALIDO_ROLE_02),
                                                 umaGrantedAuthority(USUARIO_VALIDO_ROLE_03));
        }

        GrantedAuthority umaGrantedAuthority(String auth) {
            return new SimpleGrantedAuthority(auth);
        }

    }

    @Nested
    class TemUsuarioScaAutenticado {

        @Test
        void deveriaRetornarFalsoSeContextoSegurancaEstiverVazio() {

            SecurityContextHolder.clearContext();

            assertThat(service.temUsuarioScaAutenticado()).isFalse();

        }

        @Test
        void deveriaRetornarVerdadeiroSePrincipalDoContextoSegurancaForInstanciaDeUsuarioScaAutenticado() {

            var usuarioSca = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido().build();
            var authentication = new UsernamePasswordAuthenticationToken(usuarioSca, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            assertThat(service.temUsuarioScaAutenticado()).isTrue();

        }

        @Test
        void deveriaRetornarFalsoSePrincipalDoContextoSegurancaNaoForInstanciaDeUsuarioScaAutenticado() {

            var authentication = new UsernamePasswordAuthenticationToken(new Object(), null);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            assertThat(service.temUsuarioScaAutenticado()).isFalse();

        }

        @Test
        void deveriaRetornarFalsoCasoPrincipalEstiverNulo() {

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null));

            assertThat(service.temUsuarioScaAutenticado()).isFalse();

        }

    }

    @Nested
    class GetUsuarioScaAutenticadoDoContexto {

        @Test
        void deveriaRetornarPrincipalComoUsuarioScaAutenticado() {

            var usuarioSca = UsuarioScaAutenticadoFixtures.umUsuarioValidoBemPreenchido().build();
            var authentication = new UsernamePasswordAuthenticationToken(usuarioSca, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            assertThat(service.getUsuarioScaAutenticadoDoContexto())
                    .isNotNull()
                    .isSameAs(usuarioSca);

        }

        @Test
        void deveriaRetornarNuloCasoContextoSegurancaEstiverVazaio() {

            SecurityContextHolder.clearContext();

            assertThat(service.getUsuarioScaAutenticadoDoContexto()).isNull();

        }
    }


}
