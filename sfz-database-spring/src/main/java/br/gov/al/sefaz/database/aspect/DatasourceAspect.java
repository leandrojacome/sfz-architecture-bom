package br.gov.al.sefaz.database.aspect;

import br.gov.al.sefaz.database.historico.RegistraHistoricoService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

import java.sql.Connection;

@Aspect
public class DatasourceAspect {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DatasourceAspect.class);

    private final RegistraHistoricoService registraHistoricoService;

    public DatasourceAspect(RegistraHistoricoService registraHistoricoService) {
        this.registraHistoricoService = registraHistoricoService;
    }

    @AfterReturning(value = "execution(public java.sql.Connection javax.sql.DataSource.getConnection(..))",
                    returning = "connection")
    public void afterReturning(Connection connection) {
        log.debug("Advising getConnection() AfterReturning.");
        registraHistoricoService.registrar(connection);
        log.debug("Advising getConnection() concluído AfterReturning.");
    }

}
