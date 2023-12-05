package br.gov.al.sefaz.security.configuration;

import br.gov.al.sefaz.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.Objects;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Import({SecurityBeansConfiguration.class, ProblemSupportConfiguration.class})
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityProblemSupport securityProblemSupport;

    SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                   @Autowired(required = false) SecurityProblemSupport securityProblemSupport) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.securityProblemSupport = securityProblemSupport;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(this::configureExceptionHandling)
            .authorizeHttpRequests(this::authorizeHttpRequests)
            .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void authorizeHttpRequests(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request) {
        request.requestMatchers("/management/health/**",
                                "/test/**").permitAll()
               .anyRequest().authenticated();
    }

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
        if (Objects.nonNull(securityProblemSupport)) {
            configurer.authenticationEntryPoint(securityProblemSupport)
                      .accessDeniedHandler(securityProblemSupport);
        }
    }
}
