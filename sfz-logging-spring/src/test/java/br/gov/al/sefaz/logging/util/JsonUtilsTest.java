package br.gov.al.sefaz.logging.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static br.gov.al.sefaz.logging.util.JsonUtils.mapToString;
import static org.assertj.core.api.Assertions.*;

class JsonUtilsTest {

    @Test
    void deveConverterParaJsonVazioComMapaVazio() {
        assertThat(mapToString(new HashMap<>())).isEqualTo("{}");
    }

    @Test
    void deveCoverterQuandoMapaContiverElementos() {
        Map<String, String> input = Map.of("propriedade", "valor");
        assertThat(mapToString(input)).isEqualTo("{\"propriedade\":\"valor\"}");
    }

    @Test
    void deveLancarRuntimeExceptionQuandoHouverProblemaDeConversao() {

        HashMap<Object, Object> mapaInvalido = new HashMap<>();
        mapaInvalido.put(null, "algum valor");

        assertThatThrownBy(() -> mapToString(mapaInvalido))
                .hasCauseInstanceOf(JsonProcessingException.class);

    }
}

