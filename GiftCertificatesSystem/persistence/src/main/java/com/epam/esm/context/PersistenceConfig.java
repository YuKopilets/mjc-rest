package com.epam.esm.context;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:datasource.properties")
@ComponentScan("com.epam.esm.dao")
public class PersistenceConfig {
    private static final String DRIVER_CLASS_NAME_PROPERTY = "dataSource.driverClassName";
    private static final String URL_PROPERTY = "dataSource.url";
    private static final String USERNAME_PROPERTY = "dataSource.username";
    private static final String PASSWORD_PROPERTY = "dataSource.password";

    private final Environment environment;

    public PersistenceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(environment.getProperty(DRIVER_CLASS_NAME_PROPERTY));
        config.setJdbcUrl(environment.getProperty(URL_PROPERTY));
        config.setUsername(environment.getProperty(USERNAME_PROPERTY));
        config.setPassword(environment.getProperty(PASSWORD_PROPERTY));
        return config;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
