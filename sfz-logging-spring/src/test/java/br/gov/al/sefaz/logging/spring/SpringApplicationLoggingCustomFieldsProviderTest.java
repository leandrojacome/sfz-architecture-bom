package br.gov.al.sefaz.logging.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpringApplicationLoggingCustomFieldsProviderTest {

    @InjectMocks SpringApplicationLoggingCustomFieldsProvider provider;
    @Mock Environment environment;

    @ParameterizedTest
    @MethodSource("cenariosCamposSucessoString")
    void deveRetornarValorApropriadoParaCadaCampoString(CenarioCustomField cenario) {

        when(environment.getProperty(any(),any(String.class)))
                .thenReturn("");
        when(environment.getProperty(cenario.propertySpringEsperada, ""))
                .thenReturn((String) cenario.valorCampoEsperado);

        var resultado = provider.getCustomFields();

        assertThat(resultado).extractingByKey(cenario.nomeCampoEsperado, as(STRING))
                .isEqualTo(cenario.valorCampoEsperado);
    }

    private static Stream<CenarioCustomField> cenariosCamposSucessoString() {
        return Stream.of(
                new CenarioCustomField("app_name", "sfz-test-api", "spring.application.name"),
                new CenarioCustomField("app_port", "8087", "server.port"),
                new CenarioCustomField("version", "1.0.0-SNAPSHOT", "info.project.version"),
                new CenarioCustomField("stack_nome", "stack-test", "sefaz.stack.nome"),
                new CenarioCustomField("stack_versao", "7", "sefaz.stack.versao"),
                new CenarioCustomField("stack_ambiente", "test", "sefaz.stack.ambiente"),
                new CenarioCustomField("profiles_ativos", "vault,test", "spring.profiles.active"),
                new CenarioCustomField("instance_id", "sfz-test-api:8726", "eureka.instance.instance-id")
        );
    }

    @Test
    @DisplayName("As propriedades String devem ter valor vazio caso a propriedade não seja encontrada no Spring")
    void deveriaRetornarVazioSePropriedadeNaoFoiEncontradaNoSpring() {

        when(environment.getProperty(any(),any(String.class)))
                .thenReturn("");

       var resultado = provider.getCustomFields();

        assertThat(resultado).containsOnly(
                entry("app_name", ""),
                entry("app_port", ""),
                entry("version", ""),
                entry("stack_nome", ""),
                entry("stack_versao", ""),
                entry("stack_ambiente", ""),
                entry("profiles_ativos", ""),
                entry("instance_id", "")
        );

    }

    private record CenarioCustomField(String nomeCampoEsperado,
                                      Object valorCampoEsperado,
                                      String propertySpringEsperada) {}

}
