package br.gov.al.sefaz.logging.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

public class LogbackHelper {

    public void addAppender(Appender<ILoggingEvent> appender) {
        getLoggerContext().getLogger("ROOT").addAppender(appender);
    }

    public LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

}
