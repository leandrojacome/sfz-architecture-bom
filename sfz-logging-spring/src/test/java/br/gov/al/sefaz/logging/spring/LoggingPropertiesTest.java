package br.gov.al.sefaz.logging.spring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados com a ferramenta Diffblue
 */
class LoggingPropertiesTest {
    /**
     * Method under test: {@link LoggingProperties#getLogstash()}
     */
    @Test
    void testGetLogstash() {
        LoggingProperties.Logstash actualLogstash = (new LoggingProperties()).getLogstash();
        assertEquals("sefaz-logstash", actualLogstash.getHost());
        assertFalse(actualLogstash.isEnabled());
        assertEquals(512, actualLogstash.getQueueSize());
        assertEquals(5000, actualLogstash.getPort());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LoggingProperties.Logstash#setEnabled(boolean)}
     *   <li>{@link LoggingProperties.Logstash#setHost(String)}
     *   <li>{@link LoggingProperties.Logstash#setPort(int)}
     *   <li>{@link LoggingProperties.Logstash#setQueueSize(int)}
     *   <li>{@link LoggingProperties.Logstash#getHost()}
     *   <li>{@link LoggingProperties.Logstash#getPort()}
     *   <li>{@link LoggingProperties.Logstash#getQueueSize()}
     *   <li>{@link LoggingProperties.Logstash#isEnabled()}
     * </ul>
     */
    @Test
    void testLogstashSetEnabled() {
        LoggingProperties.Logstash logstash = new LoggingProperties.Logstash();
        logstash.setEnabled(true);
        logstash.setHost("localhost");
        logstash.setPort(8080);
        logstash.setQueueSize(3);
        String actualHost = logstash.getHost();
        int actualPort = logstash.getPort();
        int actualQueueSize = logstash.getQueueSize();
        assertEquals("localhost", actualHost);
        assertEquals(8080, actualPort);
        assertEquals(3, actualQueueSize);
        assertTrue(logstash.isEnabled());
    }
}

