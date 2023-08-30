package br.gov.al.sefaz.logging.spring;

import br.gov.al.sefaz.logging.logstash.LoggingCustomFieldsProvider;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Map.Entry;

import static br.gov.al.sefaz.logging.LoggingConstants.CustomFields.*;
import static java.util.stream.Collectors.toMap;

public class SpringApplicationLoggingCustomFieldsProvider implements LoggingCustomFieldsProvider {

    private static final Map<String, String> CUSTOM_FIELD_PARA_SPRING_PROPERTY = Map.of(
            APP_NAME,"spring.application.name",
            APP_PORT,"server.port",
            VERSION,"info.project.version",
            STACK_NOME,"sefaz.stack.nome",
            STACK_VERSAO,"sefaz.stack.versao",
            STACK_AMBIENTE,"sefaz.stack.ambiente",
            PROFILES_ATIVOS,"spring.profiles.active",
            INSTANCE_ID,"eureka.instance.instance-id"
    );

    private final Environment environment;

    public SpringApplicationLoggingCustomFieldsProvider(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public Map<String, Object> getCustomFields() {
        return CUSTOM_FIELD_PARA_SPRING_PROPERTY.entrySet()
                .stream()
                .collect(toMap(Entry::getKey, this::getPropertyOuStringVazio));
    }

    private String getPropertyOuStringVazio(Entry<String, String> entry) {
        return environment.getProperty(entry.getValue(), "");
    }

}
