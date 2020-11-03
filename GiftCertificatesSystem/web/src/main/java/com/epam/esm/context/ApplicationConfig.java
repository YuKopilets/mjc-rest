package com.epam.esm.context;

import com.epam.esm.service.context.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.epam.esm")
@Import({PersistenceConfig.class, ServiceConfig.class})
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {
}
