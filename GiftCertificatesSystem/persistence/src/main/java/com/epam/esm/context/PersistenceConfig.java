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
    private static final String PACKAGE_TO_SCAN = "com.epam.esm.entity";
    private static final String DDL_AUTO_PROPERTY = "hibernate.hbm2ddl.auto";
    private static final String DIALECT_PROPERTY = "hibernate.dialect";

    private final Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(PACKAGE_TO_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(DDL_AUTO_PROPERTY, environment.getProperty(DDL_AUTO_PROPERTY));
        hibernateProperties.setProperty(DIALECT_PROPERTY, environment.getProperty(DIALECT_PROPERTY));
        return hibernateProperties;
    }
}
