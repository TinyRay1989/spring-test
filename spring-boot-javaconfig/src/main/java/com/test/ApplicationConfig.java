package com.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
    
    @Bean
    public DemoService demoService() {
        return new DemoService(demoRepository());
    }

    @Bean
    public DemoRepository demoRepository() {
        return new DemoRepository(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new DataSource();
    }
}
