package br.gov.al.sefaz.database.aspect;

import br.gov.al.sefaz.database.historico.RegistraHistoricoService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DatasourceAspectTest {

    @InjectMocks DatasourceAspect datasourceAspect;

    @Mock RegistraHistoricoService registraHistoricoService;
    @Mock private Connection connection;

    @Nested
    class NoAfterReturn {

        @Test
        void deveriaRegistrarHistoricoComConexao() {

            datasourceAspect.afterReturning(connection);

            verify(registraHistoricoService).registrar(same(connection));

        }
    }

}
