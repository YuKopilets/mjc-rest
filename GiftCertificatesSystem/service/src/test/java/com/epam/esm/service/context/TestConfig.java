package com.epam.esm.service.context;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(ServiceConfig.class)
@Profile("test")
public class TestConfig {
    @Bean
    public TagDao tagDao() {
        return Mockito.mock(TagDaoImpl.class);
    }

    @Bean
    public GiftCertificateDao giftCertificateDao() {
        return Mockito.mock(GiftCertificateDaoImpl.class);
    }
}
