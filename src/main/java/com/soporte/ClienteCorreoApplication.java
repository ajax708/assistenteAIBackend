package com.soporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClienteCorreoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClienteCorreoApplication.class, args);
    }

}
