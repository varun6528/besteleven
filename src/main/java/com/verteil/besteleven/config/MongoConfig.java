package com.verteil.besteleven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MongoConfig {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    @Bean
    public MongoInMemory mongoInMemory() {
        return new MongoInMemory(PORT, HOST);
    }
}
