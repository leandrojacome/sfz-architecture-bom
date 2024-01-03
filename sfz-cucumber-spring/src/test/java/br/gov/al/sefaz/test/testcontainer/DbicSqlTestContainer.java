package br.gov.al.sefaz.test.testcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

public class DbicSqlTestContainer implements SqlTestContainer {
    private static final Logger log = LoggerFactory.getLogger(DbicSqlTestContainer.class);
    private GenericContainer<?> dbicSQLContainer;

    @Override
    public void destroy() {
        if (null != dbicSQLContainer && dbicSQLContainer.isRunning()) {
            dbicSQLContainer.stop();
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (null == dbicSQLContainer) {
            DockerImageName dockerImageName = DockerImageName.parse("repositorio-docker.sefaz.al.gov.br/sefaz/dbic:oracle19");
            dbicSQLContainer =
                    new GenericContainer<>(dockerImageName)
                            .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"))
                            .withLogConsumer(new Slf4jLogConsumer(log))
                            .withReuse(true);
        }
        if (!dbicSQLContainer.isRunning()) {
            dbicSQLContainer.start();
        }
    }

    @Override
    public GenericContainer<?> getTestContainer() {
        return dbicSQLContainer;
    }
}
