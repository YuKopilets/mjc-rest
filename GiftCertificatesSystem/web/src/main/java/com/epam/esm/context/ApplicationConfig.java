package com.epam.esm.context;

import com.epam.esm.service.context.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.epam.esm.controller")
@Import({PersistenceConfig.class, ServiceConfig.class})
public class ApplicationConfig {
}
