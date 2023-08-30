package br.gov.al.sefaz.logging.logstash;

import br.gov.al.sefaz.logging.util.JsonUtils;
import br.gov.al.sefaz.logging.LoggingConstants;
import br.gov.al.sefaz.logging.spring.LoggingProperties;
import br.gov.al.sefaz.logging.spring.SpringApplicationLoggingCustomFieldsProvider;
import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.springframework.core.env.Environment;

import static java.net.InetSocketAddress.createUnresolved;

public class LogstashFactory {

    private final LoggingCustomFieldsProvider loggingCustomFieldsProvider;

    public LogstashFactory(LoggingCustomFieldsProvider loggingCustomFieldsProvider) {
        this.loggingCustomFieldsProvider = loggingCustomFieldsProvider;
    }

    public AsyncAppender createAsyncAppender(LoggerContext loggerContext,
                                             LoggingProperties loggingProperties,
                                             LogstashTcpSocketAppender logstashTcpSocketAppender) {
        var asyncAppender = new AsyncAppender();
        asyncAppender.setContext(loggerContext);
        asyncAppender.setName(LoggingConstants.ASYNC_LOGSTASH_APPENDER_NAME);
        asyncAppender.setQueueSize(loggingProperties.getLogstash().getQueueSize());
        asyncAppender.addAppender(logstashTcpSocketAppender);
        return asyncAppender;
    }

    public LogstashEncoder createLogstashEncoder() {
        var enconder = new LogstashEncoder();
        enconder.setCustomFields(JsonUtils.mapToString(loggingCustomFieldsProvider.getCustomFields()));
        enconder.setThrowableConverter(createShortenedThrowableConverter());
        return enconder;
    }

    public LogstashTcpSocketAppender createLogstashTcpSocketAppender(LoggerContext context,
                                                                     LoggingProperties loggingProperties,
                                                                     LogstashEncoder logstashEncoder) {
        var appender = new LogstashTcpSocketAppender();
        appender.setName(LoggingConstants.LOGSTASH_APPENDER_NAME);
        appender.setContext(context);
        appender.addDestinations(createUnresolved(
            loggingProperties.getLogstash().getHost(),
            loggingProperties.getLogstash().getPort()
        ));
        appender.setEncoder(logstashEncoder);
        return appender;
    }

    private ShortenedThrowableConverter createShortenedThrowableConverter() {
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        return throwableConverter;
    }

    public SpringApplicationLoggingCustomFieldsProvider createLoggingCustomFieldsProvider(Environment environment) {
        return new SpringApplicationLoggingCustomFieldsProvider(environment);
    }

}
