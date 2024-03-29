package pl.lodz.nbd;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import pl.lodz.nbd.common.GuesthouseFinals;

import java.net.InetSocketAddress;
import java.time.Duration;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

public class Init {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .addContactPoint(new InetSocketAddress("localhost", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandra")
                .withConfigLoader(DriverConfigLoader.programmaticBuilder()
                        .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofMillis(60000))
                        .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofMillis(60000))
                        .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(15000))
                        .build())
                .build()) {
            session.execute(createKeyspaceStatement());
        }
        System.out.println("Done creating keyspace");
    }

    private static SimpleStatement createKeyspaceStatement() {
        CreateKeyspace keyspace = createKeyspace(GuesthouseFinals.GUESTHOUSE_NAMESPACE)
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        return keyspace.build();
    }
}
