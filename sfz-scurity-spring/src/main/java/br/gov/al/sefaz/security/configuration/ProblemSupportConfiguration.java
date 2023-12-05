package br.gov.al.sefaz.security.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Configuration
@ConditionalOnBean(ProblemModule.class)
@Import(SecurityProblemSupport.class)
public class ProblemSupportConfiguration {
}
