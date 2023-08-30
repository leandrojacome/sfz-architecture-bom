package br.gov.al.sefaz.logging;

public interface LoggingConstants {

    String LOGSTASH_APPENDER_NAME = "LOGSTASH";
    String ASYNC_LOGSTASH_APPENDER_NAME = "ASYNC_LOGSTASH";

    /**
     * Campos customizados para logging.
     */
    interface CustomFields {

        String APP_NAME = "app_name";
        String APP_PORT = "app_port";
        String VERSION = "version";
        String STACK_NOME = "stack_nome";
        String STACK_VERSAO = "stack_versao";
        String STACK_AMBIENTE = "stack_ambiente";
        String PROFILES_ATIVOS = "profiles_ativos";
        String INSTANCE_ID = "instance_id";

    }

    interface Logstash {

        boolean DEFAULT_ENABLED = false;
        String DEFAULT_HOST = "sefaz-logstash";
        int DEFAULT_PORT = 5000;
        int DEFAULT_QUEUE_SIZE = 512;

    }

}
