package br.gov.al.sefaz.logging.logstash;

import br.gov.al.sefaz.logging.logback.LogbackHelper;
import br.gov.al.sefaz.logging.spring.LoggingProperties;
import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InicializadorLogstashTest {

    @Mock LoggerContext loggerContext;
    @Mock LoggingProperties loggingProperties;
    @Mock LogstashFactory logstashFactory;
    @Mock LogstashEncoder logstashEncoder;
    @Mock LogstashTcpSocketAppender logstashTcpSocketAppender;
    @Mock AsyncAppender asyncAppender;
    @Mock LogbackHelper logbackHelper;

    @InjectMocks InicializadorLogstash initializingBean;

    @Test
    void deveriaCriarUmLogstashEnconder() {

        when(logstashFactory.createLogstashTcpSocketAppender(any(), any(), any()))
                .thenReturn(logstashTcpSocketAppender);
        when(logstashFactory.createAsyncAppender(any(), any(), any())).thenReturn(asyncAppender);

        inicializarBean();

        verify(logstashFactory).createLogstashEncoder();

    }

    @Test
    void deveriaCriarUmlogstashTcpSocketAppender() {

        when(logstashFactory.createLogstashEncoder()).thenReturn(logstashEncoder);
        when(logstashFactory.createLogstashTcpSocketAppender(any(), any(), any()))
                .thenReturn(logstashTcpSocketAppender);
        when(logstashFactory.createAsyncAppender(any(), any(), any())).thenReturn(asyncAppender);

        inicializarBean();

        verify(logstashFactory).createLogstashTcpSocketAppender(same(loggerContext),
                                                                same(loggingProperties),
                                                                same(logstashEncoder));

    }

    @Test
    void deveriaStartarTcpSocketAppenderAposCriar() {

        when(logstashFactory.createLogstashEncoder()).thenReturn(logstashEncoder);
        when(logstashFactory.createLogstashTcpSocketAppender(any(), any(), any()))
                .thenReturn(logstashTcpSocketAppender);
        when(logstashFactory.createAsyncAppender(any(), any(), any())).thenReturn(asyncAppender);

        inicializarBean();

        verify(logstashTcpSocketAppender).start();

    }

    @Test
    void deveriaCriarUmAsyncAppender() {

        when(logstashFactory.createLogstashEncoder()).thenReturn(logstashEncoder);
        when(logstashFactory.createLogstashTcpSocketAppender(any(), any(), any()))
                .thenReturn(logstashTcpSocketAppender);
        when(logstashFactory.createAsyncAppender(any(), any(), any())).thenReturn(asyncAppender);

        inicializarBean();

        verify(logstashFactory).createAsyncAppender(same(loggerContext),
                                                    same(loggingProperties),
                                                    same(logstashTcpSocketAppender));


    }

    @Test
    void deveriaStartarAsyncAppender() {

        when(logstashFactory.createLogstashEncoder()).thenReturn(logstashEncoder);
        when(logstashFactory.createLogstashTcpSocketAppender(any(), any(), any()))
                .thenReturn(logstashTcpSocketAppender);
        when(logstashFactory.createAsyncAppender(any(), any(), any())).thenReturn(asyncAppender);

        inicializarBean();

        verify(asyncAppender).start();

    }

    @Test
    void deveriaAdicionarAsyncAppenderNoLoggerRaiz() {

        when(logstashFactory.createLogstashEncoder()).thenReturn(logstashEncoder);
        when(logstashFactory.createLogstashTcpSocketAppender(any(), any(), any()))
                .thenReturn(logstashTcpSocketAppender);
        when(logstashFactory.createAsyncAppender(any(), any(), any())).thenReturn(asyncAppender);

        inicializarBean();

        verify(logbackHelper).addAppender(same(asyncAppender));

    }

    private void inicializarBean() {
        initializingBean.inicializar();
    }

}
