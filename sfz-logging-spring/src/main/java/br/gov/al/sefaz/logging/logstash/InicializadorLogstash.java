package br.gov.al.sefaz.logging.logstash;

import br.gov.al.sefaz.logging.logback.LogbackHelper;
import br.gov.al.sefaz.logging.spring.LoggingProperties;
import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import jakarta.annotation.PostConstruct;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InicializadorLogstash {

    private final LoggerContext loggerContext;
    private final LoggingProperties loggingProperties;
    private final LogstashFactory logstashFactory;
    private final LogbackHelper logbackHelper;

    public InicializadorLogstash(LoggerContext loggerContext,
                                 LoggingProperties loggingProperties,
                                 LogstashFactory logstashFactory,
                                 LogbackHelper logbackHelper) {
        this.loggerContext = loggerContext;
        this.loggingProperties = loggingProperties;
        this.logstashFactory = logstashFactory;
        this.logbackHelper = logbackHelper;
    }

    @PostConstruct
    void inicializar() {
        final Logger log = LoggerFactory.getLogger(InicializadorLogstash.class);
        var logstashEncoder = logstashFactory.createLogstashEncoder();
        var logstashTcpSocketAppender = createLogstashTcpSocketAppender(logstashEncoder);
        logstashTcpSocketAppender.start();
        var asyncAppender = createAsyncAppender(logstashTcpSocketAppender);
        asyncAppender.start();
        logbackHelper.addAppender(asyncAppender);
        log.info("Logstash habilitado.");
    }

    private AsyncAppender createAsyncAppender(LogstashTcpSocketAppender logstashTcpSocketAppender) {
        return logstashFactory.createAsyncAppender(loggerContext, loggingProperties, logstashTcpSocketAppender);
    }

    private LogstashTcpSocketAppender createLogstashTcpSocketAppender(LogstashEncoder logstashEncoder) {
        return logstashFactory.createLogstashTcpSocketAppender(loggerContext, loggingProperties, logstashEncoder);
    }

}
