package com.epam.esm.context;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm.dao")
@PropertySource("classpath:hibernate.properties")
@RequiredArgsConstructor
public class PersistenceConfig {
    private static final String DDL_AUTO_PROPERTY = "hibernate.hbm2ddl.auto";
    private static final String SCAN_PACKAGE_PROPERTY = "hibernate.scan.package";
    private static final String DIALECT_PROPERTY = "hibernate.dialect";
    private static final String SHOW_SQL_PROPERTY = "hibernate.show_sql";
    private static final String FORMAT_SQL_PROPERTY = "hibernate.format_sql";

    private final Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(environment.getProperty(SCAN_PACKAGE_PROPERTY));
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(DDL_AUTO_PROPERTY, environment.getProperty(DDL_AUTO_PROPERTY));
        hibernateProperties.setProperty(DIALECT_PROPERTY, environment.getProperty(DIALECT_PROPERTY));
        hibernateProperties.setProperty(SHOW_SQL_PROPERTY, environment.getProperty(SHOW_SQL_PROPERTY));
        hibernateProperties.setProperty(FORMAT_SQL_PROPERTY, environment.getProperty(FORMAT_SQL_PROPERTY));
        return hibernateProperties;
    }
}
