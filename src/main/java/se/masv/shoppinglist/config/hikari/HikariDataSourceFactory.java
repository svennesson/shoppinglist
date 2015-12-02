package se.masv.shoppinglist.config.hikari;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.util.Duration;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Properties;

public class HikariDataSourceFactory extends DataSourceFactory {

    @NotNull
    public String dataSourceClassName;
    public boolean jdbc4Driver = false;

    @Override
    public ManagedDataSource build(MetricRegistry metricRegistry, String name) {
        final Properties properties = new Properties();
        this.getProperties()
                .entrySet()
                .stream()
                .forEach(p -> properties.setProperty(p.getKey(), p.getValue()));

        final HikariConfig config = new HikariConfig(properties);

        config.setJdbcUrl(getUrl());
        config.setUsername(getUser());
        config.setPassword(getPassword());
        Optional.ofNullable(getAutoCommitByDefault()).ifPresent(config::setAutoCommit);
        Optional.ofNullable(getReadOnlyByDefault()).ifPresent(config::setReadOnly);
        config.setCatalog(getDefaultCatalog());
        final Optional<String> isolationLevel;

        switch (getDefaultTransactionIsolation()) {
            case NONE:
                isolationLevel = Optional.of("TRANSACTION_NONE");
                break;
            case READ_UNCOMMITTED:
                isolationLevel = Optional.of("TRANSACTION_READ_UNCOMMITTED");
                break;
            case READ_COMMITTED:
                isolationLevel = Optional.of("TRANSACTION_READ_COMMITTED");
                break;
            case REPEATABLE_READ:
                isolationLevel = Optional.of("TRANSACTION_REPEATABLE_READ");
                break;
            case SERIALIZABLE:
                isolationLevel = Optional.of("TRANSACTION_SERIALIZABLE");
                break;
            case DEFAULT:
            default:
                isolationLevel = Optional.empty();
        }
        isolationLevel.ifPresent(config::setTransactionIsolation);

        config.setMinimumIdle(getMinSize());
        config.setMaximumPoolSize(getMaxSize());
        config.setConnectionInitSql(getInitializationQuery());
        config.setMaxLifetime(getMaxConnectionAge().or(Duration.minutes(30)).toMilliseconds());
        config.setConnectionTimeout(getMaxWaitForConnection().toMilliseconds());
        config.setMinimumIdle((int) getMinIdleTime().toSeconds());
        config.setValidationTimeout(getValidationQueryTimeout().or(Duration.seconds(1)).toMilliseconds());
        config.setMetricRegistry(metricRegistry);
        config.setDataSourceClassName(dataSourceClassName);

        return new ManagedHikariDataSource(config);
    }
}
