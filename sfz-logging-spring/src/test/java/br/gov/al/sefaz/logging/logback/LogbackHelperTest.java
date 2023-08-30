package br.gov.al.sefaz.logging.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked", "rawtypes"})
class LogbackHelperTest {

    @Mock Appender appender;

    @InjectMocks LogbackHelper helper;

    @Nested
    public class AoAdicionarAppender {

        /**
         * Devido a dificuldade de testar o logback com métodos estáticos e alguns tipos não <i>mockáveis</i> este teste
         * serve como teste de sanidade verificando se alguma mensagem se propaga no logger.
         */
        @Test
        void deveriaPropagarMensagemNoApenderAoEnviarMensagem() {

            helper.addAppender(appender);

            LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME).info("Mensagem");

            ArgumentCaptor<Appender> argumentCaptor = ArgumentCaptor.forClass(Appender.class);
            verify(appender).doAppend(argumentCaptor.capture());

            var mensagem = ((LoggingEvent) argumentCaptor.getValue()).getMessage();
            Assertions.assertThat(mensagem).isEqualTo("Mensagem");

        }

    }

    @Nested
    public class AoObterLogger {

        /**
         * Também trata-se de um teste de sanidade.
         */
        @Test
        void deveRetornarUmLogger() {

            var context = helper.getLoggerContext();

            Assertions.assertThat(context).isNotNull();

        }
    }

}
