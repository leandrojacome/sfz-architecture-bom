package br.gov.al.sefaz.logging.logstash;

import java.util.Map;

public interface LoggingCustomFieldsProvider {

    Map<String, Object> getCustomFields();

}
