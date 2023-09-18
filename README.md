# sfz-architecture-bom

A proposta deste projeto é estabelecer um [BOM do Maven](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) e módulos compartilhados, a fim de promover consistência 
em projetos arquiteturais.

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



### sfz-database-spring

### Integração com Oracle e histórico de banco de dados

#### Como configurar

##### 1) Habilite a integração

Numa classe *Configuration* declare a anotação `br.gov.al.sefaz.database.config.EnableSefazOracleDatabase`.

```java
@Configuration
@EnableSefazOracleDatabase
public class SefazConfiguration {
}
```

##### 2) Declare as configurações de Datasource necessárias

Atenção: não é necessário declarar tipo de Datasource, ele já é definido pela configuração.

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:DBIC
    username: admdba006
    password: admdba006
```

**Definindo um tamanho de pool maior (opcional)**

```yaml
spring:
  datasource:
    # ...
    hikari:
    	maximum-pool-size: 40
```

#### Identificação de usuário para histórico de banco de dados

Para identificar o usuário que está causando as alterações no banco de dados para fins de histórico é necessário ter um 
bean do Spring que implemente a interface `br.gov.al.sefaz.database.historico.FornecedorIdentidadeHistoricoDados`. O 
contrato desta interface informa para o histórico se as alterações correntes foram originados de uma ação de usuário 
final e quais os dados desse usuário. Sem nenhuma implementação registrada a integração não vai funcionar.

**Como registrar uma implementação própria.:**
```java
@Bean
FornecedoridentidadeHistoricoDadosImplentacao meufornecedoridentidadeHistoricoDados() {
  return new FornecedoridentidadeHistoricoDadosImplentacao()
}
```

**Implementação anônima**
Caso a aplicação não possua interações com qualquer usuário basta registrar a implementação 
`br.gov.al.sefaz.database.historico.UsuarioAnonimoFornecedoridentidadeHistoricoDados` 

```
@Bean
UsuarioAnonimoFornecedoridentidadeHistoricoDados fornecedoridentidadeHistoricoDados() {
  return new UsuarioAnonimoFornecedoridentidadeHistoricoDados();
}
```
