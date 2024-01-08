package br.gov.al.sefaz.test.cucumber;

import br.gov.al.sefaz.test.config.IntegrationTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@IntegrationTest
@Import(CucumberConfig.class)
public @interface EnableSefazCucumber {
    Class<?>[] classes() default {};
}
