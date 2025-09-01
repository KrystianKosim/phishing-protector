package org.example.phishing.protector.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableCaching
public class PhishingProtectorAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhishingProtectorAppApplication.class, args);
    }

}
