package bade.com.mx.cassandrakeyspaces.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * {@link CassandraKeyspacesConfig}
 */
@Profile({"keyspaces"})
@Configuration
@EnableCassandraRepositories
public class CassandraKeyspacesConfig extends AbstractCassandraConfiguration {

    /**
     * {@link String}
     */
    @Value("${bade.com.mx.keyspaceName}")
    private String keyspaceName;

    @Bean
    public CqlSessionFactoryBean cassandraSession() {
        CqlSessionFactoryBean factoryBean = new CqlSessionFactoryBean();
        factoryBean.setSessionBuilderConfigurer(new SessionBuilderConfigurer() {
            @Override
            public CqlSessionBuilder configure(CqlSessionBuilder cqlSessionBuilder) {
                // This config override default properties define in reference.conf file in jar resource from datastax
                // driver.
                DriverConfigLoader loader = DriverConfigLoader.fromClasspath("keyspaces");
                cqlSessionBuilder
                        .withKeyspace(keyspaceName)
                        .withConfigLoader(loader);

                return cqlSessionBuilder;
            }
        });
        factoryBean.setKeyspaceCreations(getKeyspaceCreations());
        factoryBean.setKeyspaceDrops(getKeyspaceDrops());
        factoryBean.setKeyspaceName(getKeyspaceName());

        return factoryBean;
    }

    @Bean
    public SessionFactoryFactoryBean cassandraSessionFactory(CqlSession session, CassandraConverter converter) {
        // Force to use transactions keyspace
        session.execute(String.format("USE %s", keyspaceName));

        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
        sessionFactory.setSession(session);
        sessionFactory.setConverter(converter);
        sessionFactory.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);

        return sessionFactory;
    }

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] { "bade.com.mx" };
    }

}
