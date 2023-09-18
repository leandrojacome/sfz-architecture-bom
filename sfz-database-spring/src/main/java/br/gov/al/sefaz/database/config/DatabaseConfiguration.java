package br.gov.al.sefaz.database.config;

import br.gov.al.sefaz.database.aspect.DatasourceAspect;
import br.gov.al.sefaz.database.historico.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config/oracle-database.properties")
public class DatabaseConfiguration {

    @Bean
    ChamadaProcedureHistoricoFactoryImpl chamadaProcedureHistoricoFactory() {
        return new ChamadaProcedureHistoricoFactoryImpl();
    }

    @Bean
    RegistraHistoricoServiceImpl registraHistoricoService(
            FornecedorIdentidadeHistoricoDados fornecedoridentidadeHistoricoDados,
            ChamadaProcedureHistoricoFactory chamadaProcedureHistoricoFactory) {
        return new RegistraHistoricoServiceImpl(fornecedoridentidadeHistoricoDados, chamadaProcedureHistoricoFactory);
    }

    @Bean
    DatasourceAspect datasourceAspect(RegistraHistoricoService registraHistoricoService) {
        return new DatasourceAspect(registraHistoricoService);
    }

}
