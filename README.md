# sfz-architecture-bom

A proposta deste projeto é estabelecer um [BOM do Maven](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) e módulos compartilhados, a fim de promover consistência em projetos arquiteturais.

## Desenvolvimento

* **Testes:** `mvn verify`
* **Construção:** `mvn package`

**Como testar localmente o BOM e módulos em outro projeto**

1. Execute um comando de install no projeto sfz-architecture-bom:

   ```bash
   mvn install -DskipTests
   ```

2. Declare a versão em desenvolvimento como parent no outro projeto.

## Módulos

### Architecture BOM

Para utilizar o BOM basta declarar ele como *parent*:

```xml
<parent>
    <groupId>br.gov.al.sefaz.architecture</groupId>
    <artifactId>sfz-architecture-bom</artifactId>
    <version>{{versao-bom}}</version>
    <relativePath/>
</parent>
```

*Substitui `{{versao-bom}}`* pela versão desejada.

### sfz-logging-spring

Código comum de configuração integração de *logging*.

#### Integração com Logstash

#### Como configurar

**1) Habilite a integração**

Numa classe *Configuration* declare a anotação `br.gov.al.sefaz.logging.spring.EnableSefazLogging`.

```java
@Configuration
@EnableSefazLogging
public class SefazConfiguration {
}
```

**2) Configure as propriedades**

Declare as propriedades no `application-{{profile}}.yml ` do profile desejado. Veja exemplo comentado a seguir:

```yaml
sefaz:
  logging:
    logstash:
      enabled: true # habilita a integração do logstash
      host: log.sefaz.al.gov.br # endereço do servidor do logstash
```

Alternativamente use variáveis de ambiente ou properties.

Propriedades:

* `sefaz.logging.logstash.enabled=true`
* `sefaz.logging.logstash.host=log.sefaz.al.gov.br`

Variáveis de ambiente:

* `SEFAZ_LOGGING_LOGSTASH_HOST=true`
* `SEFAZ_LOGGING_LOGSTASH_HOST=log.sefaz.al.gov.br`

#### Todas as propriedades de configuração do Logstash

- `sefaz.logging.logstash.enabled`: Habilita ou desabilita o logstash no projeto, valores possíveis: `true` e `false`.
- `sefaz.logging.logstash.host`:  *Hostname* do servidor do Logstash.
- `sefaz.logging.logstash.port`:  porta do servidor do Logstash.
- `sefaz.logging.logstash.queue-size`:  Tamanho da fila de envio assíncrona dos logs.

