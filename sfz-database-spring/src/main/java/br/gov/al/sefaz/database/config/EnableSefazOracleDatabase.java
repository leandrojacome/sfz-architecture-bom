package br.gov.al.sefaz.database.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DatabaseConfiguration.class)
public @interface EnableSefazOracleDatabase {
}
