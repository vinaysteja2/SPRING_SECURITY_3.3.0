package com.travelbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = false)
public class TravelBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelBankBackendApplication.class, args);
    }

}
