package br.gov.al.sefaz.logging.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LoggingConfiguration.class)
public @interface EnableSefazLogging {

}
