package se.masv.shoppinglist.config.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.dropwizard.db.ManagedDataSource;

public class ManagedHikariDataSource extends HikariDataSource implements ManagedDataSource {

    public ManagedHikariDataSource(HikariConfig config) {
        super(config);
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        close();
    }
}
