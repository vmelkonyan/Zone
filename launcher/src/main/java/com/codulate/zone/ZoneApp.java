package com.codulate.zone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("com.codulate.zone.properties")
public class ZoneApp {

    public static void main(String[] args) {
        log.info("Starting Zone application");
        SpringApplication.run(ZoneApp.class, args);
    }
}
