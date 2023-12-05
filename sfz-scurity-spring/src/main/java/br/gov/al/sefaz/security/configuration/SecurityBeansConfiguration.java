package br.gov.al.sefaz.security.configuration;

import br.gov.al.sefaz.security.filter.JwtAuthenticationFilter;
import br.gov.al.sefaz.security.service.AuthenticationContextService;
import br.gov.al.sefaz.security.service.JwtService;
import br.gov.al.sefaz.security.service.JwtServiceImpl;
import br.gov.al.sefaz.security.service.SpringSecurityAuthenticationContextService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBeansConfiguration {

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService,
                                                    AuthenticationContextService authenticationContextService) {
        return new JwtAuthenticationFilter(jwtService, authenticationContextService);
    }

    @Bean
    JwtServiceImpl jwtService(SecurityProperties securityProperties) {
        return new JwtServiceImpl(securityProperties);
    }

    @Bean
    SpringSecurityAuthenticationContextService springSecurityAuthenticationContextService() {
        return new SpringSecurityAuthenticationContextService();
    }

}
