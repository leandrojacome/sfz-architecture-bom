package br.gov.al.sefaz.logging.spring;

import br.gov.al.sefaz.logging.logstash.LoggingCustomFieldsProvider;
import br.gov.al.sefaz.logging.logstash.InicializadorLogstash;
import br.gov.al.sefaz.logging.logback.LogbackHelper;
import br.gov.al.sefaz.logging.logstash.LogstashFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "sefaz.logging", name = "logstash.enabled", havingValue = "true")
public class LoggingConfiguration {

    @Bean
    LogstashFactory logstashSpringFactory(LoggingCustomFieldsProvider customFieldsProvider) {
        return new LogstashFactory(customFieldsProvider);
    }

    @Bean
    LogbackHelper loggerContextConfigurer() {
        return new LogbackHelper();
    }

    @Bean
    InicializadorLogstash loggingConfigurationInitializingBean(LoggingProperties loggingProperties,
                                                               LogstashFactory logstashFactory,
                                                               LogbackHelper logbackHelper) {
        return new InicializadorLogstash(logbackHelper.getLoggerContext(),
                                         loggingProperties,
                                         logstashFactory,
                                         logbackHelper);
    }

    @Bean
    LoggingCustomFieldsProvider loggingCustomFieldsProvider(Environment environment) {
        return new SpringApplicationLoggingCustomFieldsProvider(environment);
    }

}

