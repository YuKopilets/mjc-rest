package com.epam.esm.config;

import com.epam.esm.persistence.repository.GiftCertificateFilterRepository;
import com.epam.esm.persistence.repository.impl.GiftCertificateFilterRepositoryImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@SpringBootTest
@Import(PersistenceConfig.class)
public class TestConfig {
    @Bean
    @Profile("test")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    @Profile("test")
    public GiftCertificateFilterRepository giftCertificateFilterRepository() {
        return new GiftCertificateFilterRepositoryImpl();
    }
}
