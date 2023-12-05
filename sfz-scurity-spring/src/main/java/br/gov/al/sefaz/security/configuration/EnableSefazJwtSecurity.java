package br.gov.al.sefaz.security.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(SecurityConfig.class)
public @interface EnableSefazJwtSecurity {
}
