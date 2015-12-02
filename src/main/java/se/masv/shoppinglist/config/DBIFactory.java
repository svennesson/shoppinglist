package se.masv.shoppinglist.config;

import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.setup.Environment;
import org.reflections.Reflections;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.TypedMapper;

import java.util.Set;

public class DBIFactory extends io.dropwizard.jdbi.DBIFactory{

    private final Reflections reflections;

    public DBIFactory(Reflections reflections) {
        super();
        this.reflections = reflections;
    }

    @Override
    public DBI build(Environment environment, PooledDataSourceFactory configuration, ManagedDataSource dataSource, String name) {
        final DBI jdbi = super.build(environment, configuration, dataSource, name);

        final Set<Class<? extends ResultSetMapper>> mappers = reflections.getSubTypesOf(ResultSetMapper.class);
        mappers.addAll(reflections.getSubTypesOf(TypedMapper.class));

        mappers.stream()
                .map(this::createMapper)
                .forEach(jdbi::registerMapper);

        return jdbi;
    }

    private ResultSetMapper createMapper(Class<? extends ResultSetMapper> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
