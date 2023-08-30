package br.gov.al.sefaz.logging.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

public class JsonUtils {

    private static ObjectMapper objectMapper;

    public static <K, V> String mapToString(Map<K, V> input) {
        try {
            return getObjectMapper().writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper getObjectMapper() {
        if (Objects.isNull(objectMapper)) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

}
