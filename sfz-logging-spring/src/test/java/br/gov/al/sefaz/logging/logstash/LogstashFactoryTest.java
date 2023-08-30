package br.gov.al.sefaz.logging.logstash;

import br.gov.al.sefaz.logging.LoggingConstants;
import br.gov.al.sefaz.logging.spring.LoggingProperties;
import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.net.InetSocketAddress;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogstashFactoryTest {

    @Mock LoggingCustomFieldsProvider loggingCustomFieldsProvider;
    @Mock LoggerContext loggerContext;
    @Mock LoggingProperties loggingProperties;
    @Mock LogstashEncoder logstashEncoder;
    @Mock LoggingProperties.Logstash logstashProperties;
    @Mock LogstashTcpSocketAppender logstashTcpSocketAppender;
    @Mock Environment environment;

    @InjectMocks LogstashFactory logstashFactory;

    @Test
    void deveCriarUmaInstanciaConfiguradaDoAsyncAppender() {

        when(loggingProperties.getLogstash()).thenReturn(logstashProperties);
        when(logstashProperties.getQueueSize()).thenReturn(5);

        var resultado = logstashFactory.createAsyncAppender(loggerContext, loggingProperties, logstashTcpSocketAppender);

        assertThat(resultado.getContext()).isSameAs(loggerContext);
        assertThat(resultado.getName()).isEqualTo(LoggingConstants.ASYNC_LOGSTASH_APPENDER_NAME);
        assertThat(resultado.getQueueSize()).isEqualTo(5);
        assertThat(resultado.iteratorForAppenders()).toIterable().contains(logstashTcpSocketAppender);
        assertThat(resultado.isStarted()).isFalse();

    }

    @Test
    void deveCriarUmaInstanciaConfiguradaDoLogstashTcpSocketAppender() {

        when(loggingProperties.getLogstash()).thenReturn(logstashProperties);
        when(logstashProperties.getHost()).thenReturn("localhost.localdomain");
        when(logstashProperties.getPort()).thenReturn(999);

        var resultado = logstashFactory.createLogstashTcpSocketAppender(loggerContext, loggingProperties, logstashEncoder);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getName()).isEqualTo(LoggingConstants.LOGSTASH_APPENDER_NAME);
        assertThat(resultado.getContext()).isSameAs(loggerContext);

        assertThat(resultado.getDestinations()).isNotEmpty();
        InetSocketAddress destination = resultado.getDestinations().get(0);
        assertThat(destination.getHostName()).isEqualTo("localhost.localdomain");
        assertThat(destination.getPort()).isEqualTo(999);

        assertThat(resultado.getEncoder()).isSameAs(logstashEncoder);

    }

    @Test
    void deveCriarUmaInstanciaConfiguradaDoLogstashEncoder() {

        when(loggingCustomFieldsProvider.getCustomFields()).thenReturn(Map.of("key", "value"));

        var resultado = logstashFactory.createLogstashEncoder();

        assertThat(resultado).isNotNull();
        assertThat(resultado.getCustomFields()).isEqualTo("{\"key\":\"value\"}");

        assertThat(resultado.getThrowableConverter()).isNotNull();
        var throwableConverter = (ShortenedThrowableConverter) resultado.getThrowableConverter();
        assertThat(throwableConverter.isRootCauseFirst()).isTrue();

    }

    @Test
    void deveCriarUmaInstanciaConfiguradaDoLoggingCustomFieldsProvider() {
        var resultado = logstashFactory.createLoggingCustomFieldsProvider(environment);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getEnvironment()).isNotNull();
    }

}
