package se.masv.shoppinglist.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import se.masv.shoppinglist.config.hikari.HikariDataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ShoppinglistConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new HikariDataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
