package br.gov.al.sefaz.logging.spring;

import br.gov.al.sefaz.logging.LoggingConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sefaz.logging")
public class LoggingProperties {

    private final Logstash logstash = new Logstash();

    public Logstash getLogstash() {
        return logstash;
    }

    public static class Logstash {

        private boolean enabled = LoggingConstants.Logstash.DEFAULT_ENABLED;
        private String host = LoggingConstants.Logstash.DEFAULT_HOST;
        private int port = LoggingConstants.Logstash.DEFAULT_PORT;
        private int queueSize = LoggingConstants.Logstash.DEFAULT_QUEUE_SIZE;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getQueueSize() {
            return queueSize;
        }

        public void setQueueSize(int queueSize) {
            this.queueSize = queueSize;
        }

    }

}
