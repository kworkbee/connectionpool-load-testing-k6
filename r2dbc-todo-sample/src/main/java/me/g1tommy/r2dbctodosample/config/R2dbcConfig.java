package me.g1tommy.r2dbctodosample.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(R2dbcProperties.class)
@EnableR2dbcAuditing
@EnableR2dbcRepositories
public class R2dbcConfig {

    private final R2dbcProperties r2dbcProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        final var options = ConnectionFactoryOptions.builder()
                .from(ConnectionFactoryOptions.parse(r2dbcProperties.url()))
                .option(ConnectionFactoryOptions.USER, r2dbcProperties.username())
                .option(ConnectionFactoryOptions.PASSWORD, r2dbcProperties.password())
                .build();
        final var connectionPoolConfiguration = ConnectionPoolConfiguration.builder(ConnectionFactories.get(options))
                .build();

        return new ConnectionPool(connectionPoolConfiguration);
    }
}
